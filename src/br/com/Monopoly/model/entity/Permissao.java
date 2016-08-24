/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model.entity;

import br.com.Monopoly.model.Funcionalidade;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI-Lucas
 */
@Entity
public class Permissao implements Serializable {

    @Embeddable
    public static class PermissaoID implements Serializable {

        @Enumerated
        private Funcionalidade funcionalidade;
        @ManyToOne
        private Usuario usuario;

        public PermissaoID(Funcionalidade funcionalidade, Usuario usuario) {
            this.funcionalidade = funcionalidade;
            this.usuario = usuario;
        }

        public PermissaoID() {
        }

        public Funcionalidade getFuncionalidade() {
            return funcionalidade;
        }

        public void setFuncionalidade(Funcionalidade funcionalidade) {
            this.funcionalidade = funcionalidade;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + Objects.hashCode(this.funcionalidade);
            hash = 53 * hash + Objects.hashCode(this.usuario);
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
            final PermissaoID other = (PermissaoID) obj;
            if (this.funcionalidade != other.funcionalidade) {
                return false;
            }
            if (!Objects.equals(this.usuario, other.usuario)) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    private PermissaoID id;
    private boolean ativado;
    private boolean del;

    public PermissaoID getId() {
        return id;
    }

    public void setId(PermissaoID id) {
        this.id = id;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Permissao other = (Permissao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}
