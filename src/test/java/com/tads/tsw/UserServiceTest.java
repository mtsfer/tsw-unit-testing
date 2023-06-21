package com.tads.tsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    private User sampleUser;

    @InjectMocks
    private UserService mockedService;

    @Mock
    private UserRepository mockedRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        sampleUser = generateSampleValidUser();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void Updates_When_Name_Is_Not_Null() {
        final String validName = "Daniel da Silva";

        sampleUser.setFullName(validName);

        Assertions.assertEquals(validName, sampleUser.getFullName());
    }

    @Test
    public void Do_Not_Update_When_Name_Is_Null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setFullName(null));
    }

    @Test
    public void Updates_When_Password_Is_Valid() {
        final String validPassword = "nova%Senha1002";

        sampleUser.setPassword(validPassword);

        Assertions.assertEquals(validPassword, sampleUser.getPassword());
    }

    @Test
    public void Do_Not_Update_When_Password_Has_Less_Than_8_Characters() {
        final String tooShortPassword = "#Vasc0";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPassword(tooShortPassword));
    }

    @Test
    public void Do_Not_Update_When_Password_Has_More_Than_32_Characters() {
        final String tooLargePassword = "SenhaForteAteDemais#12345678910@!#";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPassword(tooLargePassword));
    }

    @Test
    public void Do_Not_Update_When_Password_Has_No_Uppercase() {
        final String lowerCasePassword = "10@naotenhocapslock";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPassword(lowerCasePassword));
    }

    @Test
    public void Do_Not_Update_When_Password_Has_No_Numbers() {
        final String alphabeticalPassword = "#SouDeHumanas";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPassword(alphabeticalPassword));
    }

    @Test
    public void Do_Not_Update_When_Password_Is_Null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPassword(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890@example.com",
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.com",
            "email@example.name",
            "email@example.museum",
            "email@example.co.jp",
            "firstname-lastname@example.com",
            "email@example-one.com"
    })
    public void Updates_When_Email_Is_Valid(String validEmail) {
        sampleUser.setEmail(validEmail);

        Assertions.assertEquals(validEmail, sampleUser.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@example.com",
            "Joe Smith <email@example.com>",
            "email.example.com",
            "email@example@example.com",
            ".email@example.com",
            "email.@example.com",
            "email..email@example.com",
            "email@example.com (Joe Smith)",
            "email@example",
            "email@111.222.333.44444",
            "email@example..com",
            "Abc..123@example.com"
    })
    public void Do_Not_Update_When_Email_Is_Invalid(String invalidEmail) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setEmail(invalidEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "21990584320",
            "42992720515",
            "81973642019",
            "22937296391",
            "41979205739"
    })
    public void Updates_When_Phone_Number_Is_Valid(String validPhoneNumber) {
        sampleUser.setPhoneNumber(validPhoneNumber);

        Assertions.assertEquals(validPhoneNumber, sampleUser.getPhoneNumber());
    }

    @Test
    public void Do_Not_Update_When_Phone_Number_Has_Less_Than_11_Digits() {
        final String tooShortPhoneNumber = "123456";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPhoneNumber(tooShortPhoneNumber));
    }

    @Test
    public void Do_Not_Update_When_Phone_Number_Has_More_Than_11_Digits() {
        final String tooLargePhoneNumber = "8239304743013248303";

        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setPhoneNumber(tooLargePhoneNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Rua João da Silva, 37, Recife-PE",
            "Texto Aleatório 3029"
    })
    public void Updates_When_Address_Is_Valid(String validAddress) {
        sampleUser.setAddress(validAddress);

        Assertions.assertEquals(validAddress, sampleUser.getAddress());
    }

    @Test
    public void Do_Not_Update_When_Address_Is_Null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sampleUser.setAddress(null));
    }

    @Test
    public void Updates_When_User_Exists() {
        when(mockedRepository.updateUser(sampleUser)).thenReturn(Optional.of(sampleUser));

        final User updatedUser = mockedService.updateUser(sampleUser);

        Assertions.assertEquals(updatedUser.getCpf(), sampleUser.getCpf());
        verify(mockedRepository, times(1)).updateUser(sampleUser);
    }

    @Test
    public void Throws_Exception_When_Try_To_Update_Inexistent_User() {
        when(mockedRepository.updateUser(sampleUser)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> mockedService.updateUser(sampleUser));
        verify(mockedRepository, times(1)).updateUser(sampleUser);
    }


    private User generateSampleValidUser() {
        final User sampleValidUser = new User();

        sampleValidUser.setUsername("valid2023");
        sampleValidUser.setFullName("Valid User");
        sampleValidUser.setCpf("12345678910");
        sampleValidUser.setEmail("valid2023@email.com");
        sampleValidUser.setPassword("strong10#Password");
        sampleValidUser.setPhoneNumber("21987654321");
        sampleValidUser.setAddress("Avenida Paulista, 128, São Paulo - SP");
        sampleValidUser.setBirthDate("01/01/2001");

        return sampleValidUser;
    }

}
