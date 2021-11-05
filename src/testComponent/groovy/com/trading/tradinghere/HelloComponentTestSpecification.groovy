package com.trading.tradinghere

import com.trading.tradinghere.config.AbstractComponentTest
import com.trading.tradinghere.entrypoint.rest.json.DataResponse
import com.trading.tradinghere.entrypoint.rest.json.hello.HelloResponse
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

import static io.restassured.RestAssured.given

class HelloComponentTestSpecification extends AbstractComponentTest {

    def "should be a request OK status and return a phrase"() {
        given:
        String name = 'Carlos'

        when:
        Response response = given()
                .contentType(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString())
                .queryParam("name", name)
                .get("/api/v1/say-hello")

        then:
        DataResponse<HelloResponse> dataResponse = response.then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<DataResponse<HelloResponse>>() {})

        // maybe here we verify just the http status code, because on controller tests we already verify the payload response
        and:
        dataResponse
        dataResponse.t.phrase == 'Hello world my friend Carlos'
    }

    def "should be a request BAD REQUEST status and return a error payload"() {
        when:
        Response response = given()
                .contentType(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString())
                .get("/api/v1/say-hello")

        then:
        response.then().statusCode(HttpStatus.BAD_REQUEST.value())
    }
}
