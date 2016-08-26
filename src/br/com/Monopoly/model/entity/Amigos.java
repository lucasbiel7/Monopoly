/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author samuel
 */
@Entity
public class Amigos implements Serializable{
    @Embeddable
    public static class AmigosID implements Serializable {
        @ManyToOne
        private Usuario remetente;
        @ManyToOne
        private Usuario convidado;

        public AmigosID() {
        }

        public AmigosID(Usuario remetente, Usuario convidado) {
            this.remetente = remetente;
            this.convidado = convidado;
        }

        public Usuario getRemetente() {
            return remetente;
        }

        public void setRemetente(Usuario remetente) {
            this.remetente = remetente;
        }

        public Usuario getConvidado() {
            return convidado;
        }

        public void setConvidado(Usuario convidado) {
            this.convidado = convidado;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.remetente);
            hash = 97 * hash + Objects.hashCode(this.convidado);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final AmigosID other = (AmigosID) obj;
            if (!Objects.equals(this.remetente, other.remetente)) {
                return false;
            }
            if (!Objects.equals(this.convidado, other.convidado)) {
                return false;
            }
            return true;
        }
    }
    
    @EmbeddedId
    private AmigosID id;
    
    private boolean aceito;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "dateTime defaut now()",insertable = false)
    private Date dataSolicitacao;
    
    public Amigos() {
    }

    public Amigos(AmigosID id) {
        this.id = id;
    }

    public AmigosID getId() {
        return id;
    }

    public void setId(AmigosID id) {
        this.id = id;
    }

    public boolean isAceito() {
        return aceito;
    }

    public void setAceito(boolean aceito) {
        this.aceito = aceito;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Amigos other = (Amigos) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
