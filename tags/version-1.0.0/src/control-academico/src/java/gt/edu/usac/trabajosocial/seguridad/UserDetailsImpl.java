/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.trabajosocial.seguridad;

import gt.edu.usac.trabajosocial.dominio.Usuario;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
public class UserDetailsImpl implements Serializable, UserDetails {
    /** Mantiene toda la informacion del usuario */
    private Usuario usuario;

    /** Contiene los roles asignados al usuario */
    private Collection<GrantedAuthority> authorities;


    /**
     * <p>Constructor de la clase, recibe como parametro el POJO de tipo
     * {@link Usuario} el cual es el que contiene los datos del usuario.</p>
     *
     * @param usuario POJO con los datos del usuario
     */
    public UserDetailsImpl(Usuario usuario, Collection<GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo retorna un listado que contiene todos los roles que
     * el usuario tiene asignados, cada objeto dentro del listado es una
     * instacia de {@link GrantedAuthority} que no es mas que un string
     * que tiene el nombre del rol.</p>
     *
     * @return Collection Listado de roles
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
//______________________________________________________________________________
    /**
     * <p>Retorna el password de la cuenta del usuario.</p>
     *
     * @return cadena Que contiene el password
     */
    public String getPassword() {
        return this.usuario.getPassword();
    }
//______________________________________________________________________________
    /**
     * <p>Retorna el nombre de usuario de la cuenta del usuario.</p>
     *
     * @return cadena Que contiene el nombre de usuario
     */
    public String getUsername() {
        return this.usuario.getNombreUsuario();
    }
//______________________________________________________________________________
    /**
     * <p>Indica si la cuenta del usuario se encuentra activa.</p>
     *
     * @return true Si y solo si la cuenta esta activa
     */
    public boolean isEnabled() {
        return this.usuario.isHabilitado();
    }
//______________________________________________________________________________
    /**
     * <p>Indica si la cuenta del usuario expira.</p>
     *
     * @return true Si y solo si la cuenta no debe de expirar
     */
    public boolean isAccountNonExpired() {
        return true;
    }
//______________________________________________________________________________
    /**
     * <p>Indica si la cuenta del usuario se puede bloquear.</p>
     *
     * @return true Si y solo si la cuenta no se puede bloquear
     */
    public boolean isAccountNonLocked() {
        return true;
    }
//______________________________________________________________________________
    /**
     * <p>Indica si las credenciales de la cuenta del usuario expiran.</p>
     * 
     * @return true Si y solo si las credenciales no pueden expirar
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
