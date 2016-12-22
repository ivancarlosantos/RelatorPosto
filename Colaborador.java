/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorpostoeaton.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "colaboradores")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "especializacao",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("colaborador")
public class Colaborador implements Serializable {

    @OneToMany(mappedBy = "motorista")
    private List<Abastecimento> abastecimentos;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ColaboradorPK colaboradorPK;
    @Column(name = "NomFun", length = 40)
    private String nomFun;
    @Column(name = "ApeFun", length = 15)
    private String apeFun;
    @Column(name = "TipSex", length = 1)
    private String tipSex;
    @Column(name = "Tag")
    private String tag;
    @Column(name = "DatNas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datNas;
    @Column(name = "NumCpf")
    private BigInteger numCpf;
    @Column(name = "NomeEmpresa")
    private String empresa;
    @Column(name = "NumTel", length = 20)
    private String numTel;
    @Column(name = "NumCnh", length = 20)
    private String numCnh;
    @Column(name = "CatCnh", length = 4)
    private String catCnh;
    @Column(name = "DatCnh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datCnh;
    @Column(name = "VenCnh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date venCnh;
    @Column(name = "UsaFro", length = 1)
    private String usaFro;
    @Column(name = "Ativo")
    private Boolean ativo;
    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "viagem", joinColumns = {
        @JoinColumn(name = "numcad", referencedColumnName = "numcad", nullable = false, updatable = false),
        @JoinColumn(name = "numemp", referencedColumnName = "numemp"),
        @JoinColumn(name = "tipcol", referencedColumnName = "tipcol"),
        @JoinColumn(name = "reglocal", referencedColumnName = "reglocal")
    }, inverseJoinColumns = {
        @JoinColumn(name = "id")})
    private List<Viagem> viagensMotoristaCollection;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "passageiro_viagem", joinColumns = {
        @JoinColumn(name = "numcad", referencedColumnName = "numcad", nullable = false, updatable = false),
        @JoinColumn(name = "numemp", referencedColumnName = "numemp"),
        @JoinColumn(name = "tipcol", referencedColumnName = "tipcol"),
        @JoinColumn(name = "reglocal", referencedColumnName = "reglocal")
    }, inverseJoinColumns = {
        @JoinColumn(name = "viagem", referencedColumnName = "id")
    })
    private List<Viagem> viagensPassageiroCollection = new LinkedList<Viagem>();
    @Lob
    @Column(name = "foto")
    private byte[] foto;


    public Colaborador(){
        colaboradorPK = new ColaboradorPK();
    }

    /**
     * @return the colaboradorPK
     */
    public ColaboradorPK getColaboradorPK() {
        return colaboradorPK;
    }

    /**
     * @param colaboradorPK the colaboradorPK to set
     */
    public void setColaboradorPK(ColaboradorPK colaboradorPK) {
        this.colaboradorPK = colaboradorPK;
    }

    /**
     * @return the nomFun
     */
    public String getNomFun() {
        return nomFun;
    }

    /**
     * @param nomFun the nomFun to set
     */
    public void setNomFun(String nomFun) {
        this.nomFun = nomFun;
    }

    /**
     * @return the apeFun
     */
    public String getApeFun() {
        return apeFun;
    }

    /**
     * @param apeFun the apeFun to set
     */
    public void setApeFun(String apeFun) {
        this.apeFun = apeFun;
    }

    /**
     * @return the tipSex
     */
    public String getTipSex() {
        return tipSex;
    }

    /**
     * @param tipSex the tipSex to set
     */
    public void setTipSex(String tipSex) {
        this.tipSex = tipSex;
    }

    /**
     * @return the datNas
     */
    public Date getDatNas() {
        return datNas;
    }

    /**
     * @param datNas the datNas to set
     */
    public void setDatNas(Date datNas) {
        this.datNas = datNas;
    }

    /**
     * @return the numCpf
     */
    public BigInteger getNumCpf() {
        return numCpf;
    }

    /**
     * @param numCpf the numCpf to set
     */
    public void setNumCpf(BigInteger numCpf) {
        this.numCpf = numCpf;
    }

    public String getTag(){
        return this.tag;
    }

    public void setTag(String tag){
         this.tag = tag;
    }
    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the numTel
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     * @param numTel the numTel to set
     */
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    /**
     * @return the numCnh
     */
    public String getNumCnh() {
        return numCnh;
    }

    /**
     * @param numCnh the numCnh to set
     */
    public void setNumCnh(String numCnh) {
        this.numCnh = numCnh;
    }

    /**
     * @return the catCnh
     */
    public String getCatCnh() {
        return catCnh;
    }

    /**
     * @param catCnh the catCnh to set
     */
    public void setCatCnh(String catCnh) {
        this.catCnh = catCnh;
    }

    /**
     * @return the datCnh
     */
    public Date getDatCnh() {
        return datCnh;
    }

    /**
     * @param datCnh the datCnh to set
     */
    public void setDatCnh(Date datCnh) {
        this.datCnh = datCnh;
    }

    /**
     * @return the venCnh
     */
    public Date getVenCnh() {
        return venCnh;
    }

    /**
     * @param venCnh the venCnh to set
     */
    public void setVenCnh(Date venCnh) {
        this.venCnh = venCnh;
    }

    /**
     * @return the usaFro
     */
    public String getUsaFro() {
        return usaFro;
    }

    /**
     * @param usaFro the usaFro to set
     */
    public void setUsaFro(String usaFro) {
        this.usaFro = usaFro;
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

    public void getData(RondaBasicoColab basico) {
        this.setApeFun(basico.getApeFun());
        this.setAtivo(true);
        if (basico.getRondaComplementarColab() != null) {
            this.setCatCnh(basico.getRondaComplementarColab().getCatCnh());
            this.setDatCnh(basico.getRondaComplementarColab().getDatCnh());
            this.setNumCnh(basico.getRondaComplementarColab().getNumCnh());
            this.setNumTel(basico.getRondaComplementarColab().getNumTel());
            this.setVenCnh(basico.getRondaComplementarColab().getVenCnh());
        }
        this.setColaboradorPK(basico.rondaBasicoColabPK.getColaboradorPK());
        this.setDatNas(basico.getDatNas());
        this.setEmpresa(basico.getRondaEmpresa().getNomEmp());
        this.setNomFun(basico.getNomFun());
        this.setNumCpf(basico.getNumCpf());
        this.setTipSex(basico.getTipSex());
        this.setUsaFro(basico.getRondaDefAcessoColab().getUsaFro());

    }

    public String getTipoColaborador() {
        if (colaboradorPK.getTipCol() == 0) {
            return "--";
        } else if (colaboradorPK.getTipCol() == 1) {
            return "Funcionário";
        } else if (colaboradorPK.getTipCol() == 2) {
            return "Terceiro";
        } else if (colaboradorPK.getTipCol() == 3) {
            return "Parceiro";
        }
        return "";
    }

    public String getLocalRegistro() {
        if (colaboradorPK.getRegLocal() == 0) {
            return "Ronda";
        } else if (colaboradorPK.getRegLocal() == 1) {
            return "SGS";
        }
        return "";
    }

    /**
     * @return the viagensMotoristaCollection
     */
    public List<Viagem> getViagensMotoristaCollection() {
        return viagensMotoristaCollection;
    }

    /**
     * @param viagensMotoristaCollection the viagensMotoristaCollection to set
     */
    public void setViagensMotoristaCollection(List<Viagem> viagensMotoristaCollection) {
        this.viagensMotoristaCollection = viagensMotoristaCollection;
    }

    /**
     * @return the viagensPassageiroCollection
     */
    public List<Viagem> getViagensPassageiroCollection() {
        return viagensPassageiroCollection;
    }

    /**
     * @param viagensPassageiroCollection the viagensPassageiroCollection to set
     */
    public void setViagensPassageiroCollection(List<Viagem> viagensPassageiroCollection) {
        this.viagensPassageiroCollection = viagensPassageiroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colaboradorPK != null ? colaboradorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaborador)) {
            return false;
        }
        Colaborador other = (Colaborador) object;
        if ((this.colaboradorPK == null && other.colaboradorPK != null) || (this.colaboradorPK != null && !this.colaboradorPK.equals(other.colaboradorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNomFun() + " - " + getColaboradorPK().getNumCad();
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Boolean getRenderImage() {
        System.out.println("=== getRenderImage ===>" + this.getFoto());
        if ((this.getFoto() == null) || (this.getFoto().length < 1)) {
            return false;
        } else {
            return true;
        }
    }

    public String getAtivoStr() {
        if (ativo == null) {
            return null;
        }
        return ativo ? "Sim" : "Não";
    }

    public Boolean getVisualizarFoto() {
        if ((this.getFoto() != null) && (this.getFoto().length > 0)) {
            return true;
        }
        return false;
    }

    public String getNomFunAtivo() {
        String str = this.nomFun;
        if (!this.ativo) {
            str += " (inativo)";
        }
        return str;
    }
}
