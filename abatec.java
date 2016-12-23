/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorpostoeaton.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author icarlos
 */
@Entity
public class Abastecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAbastecimento;
    @ManyToOne
    private Carro carro;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "frent_tipcol", referencedColumnName = "tipcol"),
        @JoinColumn(name = "frent_reglocal", referencedColumnName = "reglocal"),
        @JoinColumn(name = "frent_numcad", referencedColumnName = "numcad"),
        @JoinColumn(name = "frent_numemp", referencedColumnName = "numemp")
    })
    private Frentista frentista;
    private String combustivel;
    private Double litros;
    private Double km;
    @ManyToOne
    private Colaborador motorista;
    private Integer liberacao;
    private String placa;
    private Boolean ativo;

    public Abastecimento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abastecimento)) {
            return false;
        }
        Abastecimento other = (Abastecimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "relatorpostoeaton.entities.Abastecimento[id=" + id + "]";
    }

    /**
     * @return the dataAbastecimento
     */
    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    /**
     * @param dataAbastecimento the dataAbastecimento to set
     */
    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    /**
     * @return the carro
     */
    public Carro getCarro() {
        return carro;
    }

    /**
     * @param carro the carro to set
     */
    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    /**
     * @return the frentista
     */
    public Frentista getFrentista() {
        return frentista;
    }

    /**
     * @param frentista the frentista to set
     */
    public void setFrentista(Frentista frentista) {
        this.frentista = frentista;
    }

    /**
     * @return the combustivel
     */
    public String getCombustivel() {
        return combustivel;
    }

    /**
     * @param combustivel the combustivel to set
     */
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    /**
     * @return the litros
     */
    public Double getLitros() {
        return litros;
    }

    /**
     * @param litros the litros to set
     */
    public void setLitros(Double litros) {
        this.litros = litros;
    }

    /**
     * @return the km
     */
    public Double getKm() {
        return km;
    }

    /**
     * @param km the km to set
     */
    public void setKm(Double km) {
        this.km = km;
    }

    /**
     * @return the motorista
     */
    public Colaborador getMotorista() {
        return motorista;
    }

    /**
     * @param motorista the motorista to set
     */
    public void setMotorista(Colaborador motorista) {
        this.motorista = motorista;
    }

    /**
     * @return the liberacao
     */
    public Integer getLiberacao() {
        return liberacao;
    }

    /**
     * @param liberacao the liberacao to set
     */
    public void setLiberacao(Integer liberacao) {
        this.liberacao = liberacao;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

