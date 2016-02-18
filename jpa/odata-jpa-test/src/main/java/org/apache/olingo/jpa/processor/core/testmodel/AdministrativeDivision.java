package org.apache.olingo.jpa.processor.core.testmodel;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import org.apache.olingo.jpa.metadata.core.edm.annotation.EdmFunctionParameter;
import org.apache.olingo.jpa.metadata.core.edm.annotation.EdmFunctions;

@EdmFunctions({
    @EdmFunction(
        name = "Siblings",
        functionName = "\"OLINGO\".\"org.apache.olingo.jpa::Siblings\"",
        isBound = false,
        returnType = @EdmFunction.ReturnType(isCollection = true) ,
        parameter = {
            @EdmFunctionParameter(name = "CodePublisher", parameterName = "\"CodePublisher\"",
                type = String.class, maxLength = 10),
            @EdmFunctionParameter(name = "CodeID", parameterName = "\"CodeID\"", type = String.class, maxLength = 10),
            @EdmFunctionParameter(name = "DivisionCode", parameterName = "\"DivisionCode\"", type = String.class,
                maxLength = 10) })
})

@IdClass(AdministrativeDivisionKey.class)
@Entity(name = "AdministrativeDivision")
@Table(schema = "\"OLINGO\"", name = "\"org.apache.olingo.jpa::AdministrativeDivision\"")
public class AdministrativeDivision {

  @Id
  @Column(name = "\"CodePublisher\"", length = 10)
  private String codePublisher;
  @Id
  @Column(name = "\"CodeID\"", length = 10)
  private String codeID;
  @Id
  @Column(name = "\"DivisionCode\"", length = 10)
  private String divisionCode;

  @Column(name = "\"CountryISOCode\"", length = 4)
  private String countryCode;
  @Column(name = "\"ParentCodeID\"", length = 10)
  private String parentCodeID;
  @Column(name = "\"ParentDivisionCode\"", length = 10)
  private String parentDivisionCode;
  @Column(name = "\"AlternativeCode\"", length = 10)
  private String alternativeCode;
  @Column(name = "\"Area\"", precision = 34, scale = 0)
  private BigDecimal area;
  @Column(name = "\"Population\"", precision = 34, scale = 0)
  private long population;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
  @JoinColumns({
      @JoinColumn(referencedColumnName = "\"CodePublisher\"", name = "\"CodePublisher\"", nullable = false,
          insertable = false, updatable = false),
      @JoinColumn(referencedColumnName = "\"CodeID\"", name = "\"ParentCodeID\"", nullable = false,
          insertable = false, updatable = false),
      @JoinColumn(referencedColumnName = "\"DivisionCode\"", name = "\"ParentDivisionCode\"", nullable = false,
          insertable = false, updatable = false) })
  private AdministrativeDivision parent;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumns({
      @JoinColumn(referencedColumnName = "\"CodePublisher\"", name = "\"CodePublisher\"", nullable = false,
          insertable = false, updatable = false),
      @JoinColumn(referencedColumnName = "\"CodeID\"", name = "\"ParentCodeID\"", nullable = false,
          insertable = false, updatable = false),
      @JoinColumn(referencedColumnName = "\"DivisionCode\"", name = "\"ParentDivisionCode\"", nullable = false,
          insertable = false, updatable = false) })
  private List<AdministrativeDivision> children;

  public String getCodePublisher() {
    return codePublisher;
  }

  public String getCodeID() {
    return codeID;
  }

  public String getDivisionCode() {
    return divisionCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getParentDivisionCode() {
    return parentDivisionCode;
  }

  public String getAlternativeCode() {
    return alternativeCode;
  }

  public AdministrativeDivision getParent() {
    return parent;
  }

  public BigDecimal getArea() {
    return area;
  }

  public long getPopulation() {
    return population;
  }

  public void setCodePublisher(String codePublisher) {
    this.codePublisher = codePublisher;
  }

  public void setCodeID(String codeID) {
    this.codeID = codeID;
  }

  public void setDivisionCode(String divisionCode) {
    this.divisionCode = divisionCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public void setParentDivisionCode(String parentDivisionCode) {
    this.parentDivisionCode = parentDivisionCode;
  }

  public void setAlternativeCode(String alternativeCode) {
    this.alternativeCode = alternativeCode;
  }

  public void setArea(BigDecimal area) {
    this.area = area;
  }

  public void setPopulation(long population) {
    this.population = population;
  }

  public String getParentCodeID() {
    return parentCodeID;
  }

  public void setParentCodeID(String parentCodeID) {
    this.parentCodeID = parentCodeID;
  }

}