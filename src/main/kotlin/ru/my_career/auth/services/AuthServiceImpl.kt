package ru.my_career.auth.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import ru.my_career._common.types.ResponseEntity
import ru.my_career.auth.dto.AuthUserDto
import ru.my_career._common.confirmations.ConfirmationStatus
import ru.my_career._common.confirmations.services.ConfirmationsService
import ru.my_career._common.constants.EXPIRE_TIME
import ru.my_career._common.database.Id
import ru.my_career._common.users.dto.CreateUserDto
import ru.my_career._common.users.dto.UserDto
import ru.my_career._common.users.services.UsersService
import ru.my_career.companies.services.CompaniesService
import ru.my_career.roles.services.RolesService
import java.util.*

class AuthServiceImpl(
    private val confirmationsService: ConfirmationsService,
    private val usersService: UsersService,
    private val rolesService: RolesService,
    private val companiesService: CompaniesService
) : AuthService {
    override suspend fun login(phoneNumber: String, code: String): ResponseEntity<AuthUserDto> {
        val confirmationRes = confirmationsService.checkCode(phoneNumber, code)

        return if (confirmationRes.payload === ConfirmationStatus.CONFIRMED) {
            val userRes = usersService.getUserByPhone(phoneNumber)
            ResponseEntity(userRes.statusCode, AuthUserDto(confirmationRes.payload, user = userRes.payload))
        } else {
            ResponseEntity(confirmationRes.statusCode, AuthUserDto(confirmationRes.payload))
        }
    }

    override fun checkCode(phoneNumber: String, code: String): ResponseEntity<ConfirmationStatus> =
        confirmationsService.checkCode(phoneNumber, code)

    override fun register(dto: CreateUserDto): ResponseEntity<AuthUserDto> {
        val confirmationRes = confirmationsService.isConfirmed(dto.phoneNumber)
        return if (confirmationRes.payload === ConfirmationStatus.CONFIRMED) {
            val userRes = usersService.createUser(dto)
            ResponseEntity(userRes.statusCode, AuthUserDto(confirmationRes.payload, user = userRes.payload))
        } else {
            ResponseEntity(confirmationRes.statusCode, AuthUserDto(confirmationRes.payload))
        }
    }

    override fun authInCompany(userId: Id, companyId: Id): ResponseEntity<AuthUserDto> {
        val userRes = usersService.getUserById(userId)
        val companyRes = companiesService.getCompanyById(companyId)

        if (userRes.payload == null || companyRes.payload == null) {
            return ResponseEntity(HttpStatusCode.BadRequest, errorMessage = "User or company not found")
        }

        val token = getToken(userId, companyId)
        return ResponseEntity(
            payload = AuthUserDto(
                confirmationStatus = ConfirmationStatus.CONFIRMED,
                jwtToken = token,
                user = userRes.payload,
                company = companyRes.payload
            )
        )
    }

    private fun getToken(userId: Id, companyId: Int): String {
        val permissionsRes = rolesService.getUserPermissionsForCompany(userId, companyId)
        val permissionsTitles = permissionsRes.payload?.map { it.title }?.distinct()!!.toTypedArray()

        return JWT.create()
            .withAudience("http://0.0.0.0:8080/hello")
            .withIssuer("http://0.0.0.0:8080/")
            .withClaim("userId", userId)
            .withClaim("companyId", companyId)
            .withArrayClaim("permissions", permissionsTitles)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRE_TIME))
            .sign(Algorithm.HMAC256("secret"))
    }
}