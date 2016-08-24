/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI-Lucas
 */
@Entity
public class Jogador implements Serializable {

    @Embeddable
    public static class JogadorID implements Serializable {

        @ManyToOne
        private Usuario usuario;
        @ManyToOne
        private Sala sala;

        public JogadorID(Usuario usuario, Sala sala) {
            this.usuario = usuario;
            this.sala = sala;
        }

        public JogadorID() {
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public Sala getSala() {
            return sala;
        }

        public void setSala(Sala sala) {
            this.sala = sala;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.usuario);
            hash = 53 * hash + Objects.hashCode(this.sala);
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
            final JogadorID other = (JogadorID) obj;
            if (!Objects.equals(this.usuario, other.usuario)) {
                return false;
            }
            if (!Objects.equals(this.sala, other.sala)) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    private JogadorID id;
    private boolean criador;
    private boolean del;

    public JogadorID getId() {
        return id;
    }

    public void setId(JogadorID id) {
        this.id = id;
    }

    public boolean isCriador() {
        return criador;
    }

    public void setCriador(boolean criador) {
        this.criador = criador;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return getId().getUsuario().toString();
    }
}
