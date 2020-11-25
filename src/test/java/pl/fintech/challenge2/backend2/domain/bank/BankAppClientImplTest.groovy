package pl.fintech.challenge2.backend2.domain.bank

import org.springframework.web.client.RestTemplate
import pl.fintech.challenge2.backend2.domain.user.User
import spock.lang.Specification
import spock.lang.Unroll

class BankAppClientImplTest extends Specification {

    def restTemplate = new RestTemplate()
    def bankAppClient = new BankAppClientImpl(restTemplate)

    def "CreateAccount"() {
        when:
        def accountNumber = bankAppClient.createAccount("email")
        then:
        assert accountNumber.length() > 0
    }

    @Unroll
    def "GetAccountInfo #a"() {
        when:
        def account = bankAppClient.getAccountInfo(a);
        then:
        assert account.getAccountBalance() > 0
        where:
        a << ["631b7cb4-daca-4c35-ab10-85e4c4b30c26", "95bd9b19-9465-4403-8f4e-d9f17ebb82a9"]
    }

    @Unroll
    def "CreateInternalTransaction between user1 and user 2 and then between user2 and user1"() {
        when:
        def amount1 = bankAppClient.getAccountInfo(user1.getAccountNumber()).getAccountBalance()
        def amount2 = bankAppClient.getAccountInfo(user2.getAccountNumber()).getAccountBalance()
        bankAppClient.createInternalTransaction(user1, user2, BigDecimal.valueOf(100))
        then:
        assert (amount1 - 100) == bankAppClient.getAccountInfo(user1.getAccountNumber()).getAccountBalance()
        assert (amount2 + 100) == bankAppClient.getAccountInfo(user2.getAccountNumber()).getAccountBalance()
        where:
        user1 | user2
        createUser("631b7cb4-daca-4c35-ab10-85e4c4b30c26") | createUser("95bd9b19-9465-4403-8f4e-d9f17ebb82a9")
        createUser("95bd9b19-9465-4403-8f4e-d9f17ebb82a9") | createUser("631b7cb4-daca-4c35-ab10-85e4c4b30c26")
    }

    def"CreateExternalPayment and then withdrawal"() {
        given:
        def user = createUser("95bd9b19-9465-4403-8f4e-d9f17ebb82a9")
        when:
        def amount1 = bankAppClient.getAccountInfo(user.getAccountNumber()).getAccountBalance()
        bankAppClient.createExternalPayment(user, BigDecimal.valueOf(100));
        then:
        assert amount1 + 100 == bankAppClient.getAccountInfo(user.getAccountNumber()).getAccountBalance()
        when:
        bankAppClient.createExternalWithdrawal(user, BigDecimal.valueOf(100));
        then:
        assert amount1 == bankAppClient.getAccountInfo(user.getAccountNumber()).getAccountBalance()

    }

    User createUser(String accountNumber) {
        return User.builder()
                    .accountNumber(accountNumber).build()
    }

}
