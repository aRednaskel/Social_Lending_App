package pl.fintech.challenge2.backend2.domain.bank

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class BankAppClientTest extends Specification {

    def restTemplate = new RestTemplate()
    def bankAppClient = new BankAppClient(restTemplate)

    def "CreateAccount"() {
        when:
        def accountNumber = bankAppClient.createAccount("email")
        then:
        assert accountNumber.length() > 0
    }

    def "GetAccountInfo"() {
        when:
        def account = bankAppClient.getAccountInfo("95bd9b19-9465-4403-8f4e-d9f17ebb82a9")
        then:
        assert account.getAccountBalance() == 0
    }


}
