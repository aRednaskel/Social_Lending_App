package pl.fintech.challenge2.backend2.domain.bank

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class BankAppClientImplTest extends Specification {

    def restTemplate = new RestTemplate()
    def bankAppClient = new BankAppClientImpl(restTemplate)

    def "CreateAccount"() {
        when:
        def accountNumber = bankAppClient.createAccount("email")
        then:
        assert accountNumber.length() > 0
    }

    def "GetAccountInfo"() {
        when:
//        631b7cb4-daca-4c35-ab10-85e4c4b30c26
        def account = bankAppClient.getAccountInfo("95bd9b19-9465-4403-8f4e-d9f17ebb82a9")
        then:
        assert account.getAccountBalance() == 0
    }


}
