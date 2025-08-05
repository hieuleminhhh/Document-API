package com.example.documentapi;

import com.example.documentapi.controller.DocumentController;
import com.example.documentapi.dto.*;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        @Test
        public void createDocument_Success() throws Exception {
                UUID documentId = UUID.randomUUID();
                DocumentRequest request = new DocumentRequest();

                request.setTaxCode("123456789");
                request.setCompanyName("Test Company");
                request.setAddress("123 Test Street");
                request.setCompanyPhoneNumber("0123456789");
                request.setCompanyFax("0123456789");
                request.setCompanyEmail("test@example.com");
                request.setProvinceCode("P001");
                request.setWardCode("W001");
                request.setDocumentType("Invoice");
                request.setStatusId(UUID.randomUUID());
                request.setNswCode("NSW001");

                Mockito.when(documentService.createDocument(any(DocumentRequest.class))).thenReturn(documentId);

                mockMvc.perform(post("/api/documents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.success", is(true)))
                                .andExpect(jsonPath("$.message", is("Document created successfully")))
                                .andExpect(jsonPath("$.data", is(documentId.toString())));
        }

        @Test
        public void saveDocumentHistory_Success() throws Exception {
                DocumentHistoryRequest request = new DocumentHistoryRequest();
                request.setDocumentId(UUID.randomUUID());
                request.setTitle("History Title");
                request.setContent("Content of history");
                request.setReason("Some reason"); // optional, nhưng nên set nếu muốn
                request.setAction("CREATE");
                request.setMessageContent("Message content here");

                Mockito.doNothing().when(documentService).saveDocumentHistory(eq(request.getDocumentId()),
                                any(DocumentHistoryRequest.class));

                mockMvc.perform(post("/api/documents/history")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success", is(true)))
                                .andExpect(jsonPath("$.message", is("Document history saved successfully")))
                                .andExpect(jsonPath("$.data.documentId", is(request.getDocumentId().toString())));
        }

        @Test
        public void saveDocumentHistory_MissingDocumentId() throws Exception {
                DocumentHistoryRequest request = new DocumentHistoryRequest();
                request.setDocumentId(null);
                request.setTitle("Some title");
                request.setContent("Some content");
                request.setAction("UPDATE");

                mockMvc.perform(post("/api/documents/history")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.success", is(false)))
                                .andExpect(jsonPath("$.message", is("Document ID is required")));
        }

        @Test
        public void saveDocumentHistory_Exception() throws Exception {
                DocumentHistoryRequest request = new DocumentHistoryRequest();
                request.setDocumentId(UUID.randomUUID());
                // Set các trường bắt buộc
                request.setTitle("Some title");
                request.setContent("Some content");
                request.setAction("UPDATE");

                Mockito.doThrow(new RuntimeException("DB error"))
                                .when(documentService)
                                .saveDocumentHistory(eq(request.getDocumentId()), any(DocumentHistoryRequest.class));

                mockMvc.perform(post("/api/documents/history")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.success", is(false)))
                                .andExpect(jsonPath("$.message", containsString("Error saving document history")));
        }

        @Test
        public void saveDocumentAttachment_Success() throws Exception {
                DocumentAttachmentRequest request = new DocumentAttachmentRequest();
                request.setDocumentId(UUID.randomUUID());
                request.setFileId(UUID.randomUUID());
                request.setFileName("example.pdf");
                request.setFileLink("http://example.com/example.pdf");
                request.setAttachmentTypeCode("TYPE01");

                Mockito.doNothing().when(documentService).saveDocumentAttachment(eq(request.getDocumentId()),
                                any(DocumentAttachmentRequest.class));

                mockMvc.perform(post("/api/documents/attachments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success", is(true)))
                                .andExpect(jsonPath("$.message", is("Document attachment saved successfully")))
                                .andExpect(jsonPath("$.data.documentId", is(request.getDocumentId().toString())));
        }

        @Test
        public void saveDocumentAttachment_MissingDocumentId() throws Exception {
                DocumentAttachmentRequest request = new DocumentAttachmentRequest();
                request.setDocumentId(null); // test thiếu documentId
                request.setFileName("example.pdf");
                request.setFileLink("http://example.com/example.pdf");
                request.setAttachmentTypeCode("TYPE01");

                mockMvc.perform(post("/api/documents/attachments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.success", is(false)))
                                .andExpect(jsonPath("$.message", is("Document ID is required")));
        }

        @Test
        public void saveDocumentAttachment_Exception() throws Exception {
                DocumentAttachmentRequest request = new DocumentAttachmentRequest();
                request.setDocumentId(UUID.randomUUID());
                request.setFileName("example.pdf");
                request.setFileLink("http://example.com/example.pdf");
                request.setAttachmentTypeCode("TYPE01");

                Mockito.doThrow(new RuntimeException("DB error"))
                                .when(documentService)
                                .saveDocumentAttachment(eq(request.getDocumentId()),
                                                any(DocumentAttachmentRequest.class));

                mockMvc.perform(post("/api/documents/attachments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.success", is(false)))
                                .andExpect(jsonPath("$.message", containsString("Error saving document attachment")));
        }

}
