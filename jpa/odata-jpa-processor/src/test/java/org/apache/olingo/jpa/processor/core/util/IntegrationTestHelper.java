package org.apache.olingo.jpa.processor.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.jpa.metadata.api.JPAEdmProvider;
import org.apache.olingo.jpa.processor.core.api.JPAODataContextAccess;
import org.apache.olingo.jpa.processor.core.api.JPAOdataRequestProcessor;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.edmx.EdmxReference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IntegrationTestHelper {
  public final HttpServletRequestDouble req;
  public final HttpServletResponseDouble resp;
  private static final String uriPrefix = "http://localhost:8080/Test/Olingo.svc/";
  private static final String PUNIT_NAME = "org.apache.olingo.jpa";

  public IntegrationTestHelper(EntityManagerFactory localEmf, String urlPath) throws IOException,
      ODataException {
    this(localEmf, null, urlPath);
  }

  public IntegrationTestHelper(EntityManagerFactory localEmf, DataSource ds, String urlPath) throws IOException,
      ODataException {
    super();
    this.req = new HttpServletRequestDouble(uriPrefix + urlPath);
    this.resp = new HttpServletResponseDouble();
    OData odata = OData.newInstance();
    JPAODataContextAccess context = new JPAODataContextAccessDouble(new JPAEdmProvider(PUNIT_NAME, localEmf, null), ds);

    ODataHttpHandler handler = odata.createHandler(odata.createServiceMetadata(context.getEdmProvider(),
        new ArrayList<EdmxReference>()));
    handler.register(new JPAOdataRequestProcessor(context, localEmf.createEntityManager()));
    handler.process(req, resp);
  }

  public HttpServletResponseDouble getResponce() {
    return resp;
  }

  public int getStatus() {
    return resp.getStatus();
  }

  public String getRawResult() throws IOException {
    InputStream in = resp.getInputStream();
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String read;

    while ((read = br.readLine()) != null) {
      sb.append(read);
    }
    br.close();
    return sb.toString();
  }

  public ArrayNode getValues() throws JsonProcessingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(getRawResult());
    if (!(node.get("value") instanceof ArrayNode))
      fail("Wrong result type; ArrayNode expected");
    ArrayNode values = (ArrayNode) node.get("value");
    return values;
  }

  public ObjectNode getValue() throws JsonProcessingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode value = mapper.readTree(getRawResult());
    if (!(value instanceof ObjectNode))
      fail("Wrong result type; ObjectNode expected");
    return (ObjectNode) value;
  }

  public void assertStatus(int exp) throws IOException {
    assertEquals(getRawResult(), exp, getStatus());

  }
}