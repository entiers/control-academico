/*
 * Sistema de Control Academico
 * Escuela de Trabajo Social
 * Universidad de San Carlos de Guatemala
 */

package gt.edu.usac.cats.seguridad;

import gt.edu.usac.cats.dao.DaoGeneral;
import gt.edu.usac.cats.dominio.Catedratico;
import gt.edu.usac.cats.dominio.Estudiante;
import gt.edu.usac.cats.dominio.Rol;
import gt.edu.usac.cats.dominio.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel Castillo
 * @version 1.0
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    /** Objeto de acceso a datos, permite realizar operacion en la base de datos */
    @Resource
    private DaoGeneral daoGeneralImpl;


    /**
     * <p>Constructor de la clase.</p>
     */
    public UserDetailsServiceImpl() {}
//______________________________________________________________________________
    /**
     * <p>Este metodo es el que utiliza el Authentication Provider para obtener
     * las credenciales y roles de los usuario. Estos datos del usuario se
     * retorna en un objeto que implementa la interface {@link UserDetails}. La
     * busqueda del usuario se realiza por medio de su nombre de usuario.</p>
     *
     * @param nombreUsuario Nombre de usuario del que se quieren obtener sus
     *        datos
     * @return UserDetails Objeto que contiene las credenciales y los roles
     *         del usuario
     * @throws UsernameNotFoundException Se lanza si el usuario no existe
     * @throws DataAccessException Se lanza si ocurrio un error con el acceso
     *         a datos
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException, DataAccessException {
        // se busca el usuario por su nombre
        Usuario usuario = this.getUsuarioPorNombreUsuario(nombreUsuario);
        

        // el usuario no existe
        if(usuario == null)
            throw new UsernameNotFoundException("El usuario " + nombreUsuario + " no se encontro");

        // se obtienen los roles del usuario
        List<Rol> roles = this.getRolesUsuario(usuario);

        // se realiza la conversion de los roles
        ArrayList<GrantedAuthority> authorities = this.getAuthorities(roles);

        // se retornan las credenciales y roles del usuario

        UserDetailsImpl udi = new UserDetailsImpl(usuario, authorities);
        udi = this.asignarNombreIdUsuario(udi);
        return udi;
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo se encarga de buscar los datos del usuario por medio
     * de su nombre de usuario, si la busquena no obtiene resultados se
     * retorna null.</p>
     *
     * @param nombreUsuario String que tiene el nombre de usuario
     * @return Usuario Pojo que contiene los datos del usuario o <code>null</code>
     *         si no se encuentra el usuario
     * @throws DataAccessException Si ocurre un error con el acceso a los datos
     */
    private Usuario getUsuarioPorNombreUsuario(String nombreUsuario) throws DataAccessException {
        // se crea la consulta para obtener el usuario por medio de su nombre
        DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class);
        criteria.add(Restrictions.eq("nombreUsuario", nombreUsuario));

        // se trata de obtener el usuario, si el usuario no existe
        // se retorna null
        
        return this.daoGeneralImpl.uniqueResult(criteria);
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo retorna todos los roles que estan asignados al usuario
     * que se envia como parametro. Si el usuario no tiene roles asignados
     * se retorna una lista vacia.</p>
     *
     * @param usuario Pojo que contiene los datos del usuario
     * @return List Listado de los roles asignados al usuario
     */
    private List<Rol> getRolesUsuario(Usuario usuario) {
        // se crea la consulta para obtener todos los roles del usuario
        DetachedCriteria criteria1 = DetachedCriteria.forClass(Rol.class);
        DetachedCriteria criteria2 = criteria1.createCriteria("asignacionRolPerfils");
        DetachedCriteria criteria3 = criteria2.createCriteria("perfil");
        DetachedCriteria criteria4 = criteria3.createCriteria("asignacionUsuarioPerfils");
        criteria4.add(Restrictions.eq("usuario", usuario));

        // retorna todo los roles, si no encuentra roles retorna la lista vacia
        List<Rol> roles = this.daoGeneralImpl.find(criteria1);
        for(Rol rol:roles){
            System.out.println("__rol__: "+rol.getNombre());
        }
        return roles;
        
    }
//______________________________________________________________________________
    /**
     * <p>Este metodo realiza la conversion de los roles de un usuario al
     * formato que entiende Spring Security, este necesita que los roles se
     * pasen en un listado que contenga objetos {@link GrantedAuthority} los
     * cuales son los que contiene la cadena con el nombre del rol.</p>
     * 
     * @param roles Listado de roles del usuario
     * @return ArrayList Listado que contiene los roles en el formato que
     *         utiliza Spring Security
     */
    private ArrayList<GrantedAuthority> getAuthorities(List<Rol> roles) {
        // se crea el listado que contendra todos los GrantedAuthority que no
        // son mas que los roles del usuario trasladados al formato que
        // maneja Spring Security
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Rol rol : roles) {
            String r = "ROLE_" + rol.getNombre().toUpperCase();
            authorities.add(new GrantedAuthorityImpl(r));
        }

        return authorities;
    }
    
    // obtener el nombre del usuario e identificacion, dependiendo si es persona, estudiante o catedratico
        private UserDetailsImpl asignarNombreIdUsuario(UserDetailsImpl usr) throws DataAccessException {
        // verifica si es Persona
        if (usr.getUsuario().getPersona() == null){
            DetachedCriteria criteria = DetachedCriteria.forClass(Estudiante.class);
            criteria.add(Restrictions.eq("carne", usr.getUsername()));
            Estudiante est = (Estudiante)this.daoGeneralImpl.uniqueResult(criteria);  
            //System.out.println(" estudiante"+est);
            if (est == null){ // no es estudiante, debe ser catedratico
                criteria = DetachedCriteria.forClass(Catedratico.class);
                //System.out.println("Codigo = "+usr.getUsername());
                criteria.add(Restrictions.eq("codigo", usr.getUsername()));
                Catedratico cat = (Catedratico)this.daoGeneralImpl.uniqueResult(criteria);  
                if (cat != null){
                    //System.out.println("Catedratico "+cat);      
                    usr.setNombreCompleto(cat.getNombre()+" "+cat.getApellido());
                    usr.setIdentificacion(cat.getCodigo());
                }
            }else{
                 usr.setNombreCompleto(est.getNombre());
                usr.setIdentificacion(est.getCarne()); 
            }
           
        }else{
            usr.setNombreCompleto(usr.getUsuario().getPersona().getNombre());
            usr.setIdentificacion(usr.getUsuario().getNombreUsuario());
        }
            

        // se devuelve el objeto con la informacion adicional
         return usr;
    }
}
