package com.example.documentapi;

import com.example.documentapi.controller.DocumentController;
import com.example.documentapi.dto.DocumentRequest;
import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.dto.DocumentSearchRequest;
import com.example.documentapi.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;
    // ok
    @Test
    void testCreateDocument_Success() throws Exception {
        UUID mockId = UUID.randomUUID();

        DocumentRequest request = new DocumentRequest();
        request.setCompanyName("Example Company");
        request.setTaxCode("123456789");
        request.setAddress("123 Main St");
        request.setCompanyPhoneNumber("0123456789");
        request.setCompanyFax("0123456788");
        request.setCompanyEmail("contact@example.com");
        request.setProvinceCode("01");
        request.setWardCode("001");
        request.setDocumentType("Invoice");
        request.setStatusId(UUID.randomUUID());
        request.setNswCode("NSW123");

        Mockito.when(documentService.createDocument(Mockito.any())).thenReturn(mockId);

        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(mockId.toString())));
    }

    @Test
    void testSearchDocuments_Success() throws Exception {
        DocumentSearchRequest searchRequest = new DocumentSearchRequest();
        searchRequest.setTaxCode("1234567890");
        searchRequest.setCompanyName("company name 1");
        searchRequest.setProvinceCode("01");
        searchRequest.setDocumentType("BCT0600099");
        searchRequest.setStatusId("0ba950dd-6a0c-429b-8dae-811c71f94f52");
        searchRequest.setPage(0);
        searchRequest.setSize(10);

        Page<DocumentSearchProjection> emptyPage = new PageImpl<>(
                Collections.emptyList(), PageRequest.of(0, 10), 0);

        Mockito.when(documentService.searchDocuments(Mockito.any())).thenReturn(emptyPage);

        mockMvc.perform(post("/api/documents/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10)));
    }
    // fail
    @Test
    void testCreateDocument_Fail_MissingCompanyName() throws Exception {
        DocumentRequest request = new DocumentRequest();
        request.setCompanyName(""); // Không hợp lệ, @NotBlank
        request.setTaxCode("123456789");
        request.setAddress("123 Main St");
        request.setCompanyPhoneNumber("0123456789");
        request.setCompanyFax("0123456788");
        request.setCompanyEmail("contact@example.com");
        request.setProvinceCode("01");
        request.setWardCode("001");
        request.setDocumentType("Invoice");
        request.setStatusId(UUID.randomUUID());
        request.setNswCode("NSW123");

        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // Expect lỗi 400
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message").value("companyName không được để trống"));
    }

    @Test
    void testCreateDocument_Fail_InvalidEmail() throws Exception {
        DocumentRequest request = new DocumentRequest();
        request.setCompanyName("Example Company");
        request.setTaxCode("123456789");
        request.setCompanyEmail("invalid-email");
        request.setAddress("123 Main St");
        request.setCompanyPhoneNumber("0123456789");
        request.setCompanyFax("0123456788");
        request.setProvinceCode("01");
        request.setWardCode("001");
        request.setDocumentType("Invoice");
        request.setStatusId(UUID.randomUUID());
        request.setNswCode("NSW123");

        mockMvc.perform(post("/api/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message").value("Email không hợp lệ"));
    }

}
