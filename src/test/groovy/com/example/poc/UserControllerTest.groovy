package com.example.poc

import com.example.poc.controller.dto.CarDto
import com.example.poc.controller.dto.UserDto
import com.example.poc.repository.entity.BodyType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.IntStream

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ObjectMapper objectMapper

    private static final def TEXT_DESCRIPTION = 'War has changed. It’s no longer about nations, ideologies, or ethnicity.' +
            ' It’s an endless series of proxy battles fought by mercenaries and machines. ' +
            'War – and its consumption of life – has become a well-oiled machine. ' +
            'War has changed. ID-tagged soldiers carry ID-tagged weapons, use ID-tagged gear. ' +
            'Nanomachines inside their bodies enhance and regulate their abilities. ' +
            'Genetic control. Information control. Emotion control. Battlefield control. ' +
            'Everything is monitored and kept under control. War has changed. ' +
            'The age of deterrence has become the age of control . . . ' +
            'All in the name of averting catastrophe from weapons of mass destruction. ' +
            'And he who controls the battlefield . . . controls history. War has changed. ' +
            'When the battlefield is under total control . . . War becomes routine.'

    private static final def XML_DESCRIPTION = '<!DOCTYPE glossary PUBLIC "-//OASIS//DTD DocBook V3.1//EN">\n' +
            ' <glossary><title>example glossary</title>\n' +
            '  <GlossDiv><title>S</title>\n' +
            '   <GlossList>\n' +
            '    <GlossEntry ID="SGML" SortAs="SGML">\n' +
            '     <GlossTerm>Standard Generalized Markup Language</GlossTerm>\n' +
            '     <Acronym>SGML</Acronym>\n' +
            '     <Abbrev>ISO 8879:1986</Abbrev>\n' +
            '     <GlossDef>\n' +
            '      <para>A meta-markup language, used to create markup\n' +
            'languages such as DocBook.</para>\n' +
            '      <GlossSeeAlso OtherTerm="GML">\n' +
            '      <GlossSeeAlso OtherTerm="XML">\n' +
            '     </GlossDef>\n' +
            '     <GlossSee OtherTerm="markup">\n' +
            '    </GlossEntry>\n' +
            '   </GlossList>\n' +
            '  </GlossDiv>\n' +
            ' </glossary>'

    private static final def JSON_DESCRIPTION = '{\n' +
            '    "glossary": {\n' +
            '        "title": "example glossary",\n' +
            '\t\t"GlossDiv": {\n' +
            '            "title": "S",\n' +
            '\t\t\t"GlossList": {\n' +
            '                "GlossEntry": {\n' +
            '                    "ID": "SGML",\n' +
            '\t\t\t\t\t"SortAs": "SGML",\n' +
            '\t\t\t\t\t"GlossTerm": "Standard Generalized Markup Language",\n' +
            '\t\t\t\t\t"Acronym": "SGML",\n' +
            '\t\t\t\t\t"Abbrev": "ISO 8879:1986",\n' +
            '\t\t\t\t\t"GlossDef": {\n' +
            '                        "para": "A meta-markup language, used to create markup languages such as DocBook.",\n' +
            '\t\t\t\t\t\t"GlossSeeAlso": ["GML", "XML"]\n' +
            '                    },\n' +
            '\t\t\t\t\t"GlossSee": "markup"\n' +
            '                }\n' +
            '            }\n' +
            '        }\n' +
            '    }\n' +
            '}'

    private static final def DESCRIPTIONS = [TEXT_DESCRIPTION, XML_DESCRIPTION, JSON_DESCRIPTION]



    @Unroll
    def 'that can create 10k users one by one request'() {
        given:
        def startTime = System.currentTimeMillis()
        def rand = new Random()
        def resultList = new ArrayList<MvcResult>()
        def cars = getCars()

        when:
        IntStream.range(0, 10000)
                .forEach(i -> resultList.add(mvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(new UserDto(null, "name",
                                DESCRIPTIONS.get(rand.nextInt(3)), cars))
                ))
                .andReturn()))

        then:
        def stopTime = System.currentTimeMillis()
        resultList.size() == 10000
        System.out.println("Elapsed time: " + (stopTime - startTime) / 1000.0)
    }

    @Unroll
    def 'that can create 10k users in batch'() {
        given:
        def startTime = System.currentTimeMillis()
        def rand = new Random()
        def resultList = new ArrayList<UserDto>()
        def cars = getCars()

        IntStream.range(0, 10000)
                .forEach(i -> resultList.add(
                        new UserDto(null, "name",
                                DESCRIPTIONS.get(rand.nextInt(3)), cars)
                ))

        when:
        def result = mvc.perform(MockMvcRequestBuilders.post("/api/user/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resultList)))
                .andReturn()

        then:
        def stopTime = System.currentTimeMillis()
        result.getResponse().getStatus() == 200
        System.out.println("Elapsed time: " + (stopTime - startTime) / 1000.0)
    }

    List<CarDto> getCars() {
        def firstCar = objectMapper.readValue(mvc.perform(MockMvcRequestBuilders.post("/api/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CarDto(null, "Honda", BodyType.COUPE))))
                .andReturn().getResponse().getContentAsString(), CarDto.class)

        def secondCar = objectMapper.readValue(mvc.perform(MockMvcRequestBuilders.post("/api/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CarDto(null, "Toyota", BodyType.HATCHBACK))))
                .andReturn().getResponse().getContentAsString(), CarDto.class)

        def thirdCar = objectMapper.readValue(mvc.perform(MockMvcRequestBuilders.post("/api/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CarDto(null, "Opel", BodyType.SUV))))
                .andReturn().getResponse().getContentAsString(), CarDto.class)

        return List.of(firstCar, secondCar, thirdCar)
    }
}
