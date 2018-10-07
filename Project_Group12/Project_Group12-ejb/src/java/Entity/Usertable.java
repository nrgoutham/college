/*
 *
 * Arjun Ramesh                 16044215 
 * Dakshina Murthy              16064879
 * Gowtham NR                   16029143
 * Ruwaid Khateeb               16055314
 * Vivek Padmanaban chinnaraj   15113582
 *
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "USERTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertable.findAll", query = "SELECT u FROM Usertable u")
    , @NamedQuery(name = "Usertable.findByUid", query = "SELECT u FROM Usertable u WHERE u.uid = :uid")
    , @NamedQuery(name = "Usertable.loginValidate", query = "SELECT COUNT(u) FROM Usertable u WHERE u.username = :username AND u.password = :password")
    , @NamedQuery(name = "Usertable.loginValidate2", query = "SELECT COUNT(u) FROM Usertable u WHERE u.username = :username AND u.password = :password AND u.usertype = :usertype")
    , @NamedQuery(name = "Usertable.getUserID", query = "SELECT u.uid FROM Usertable u WHERE u.username = :username AND u.password = :password")
    , @NamedQuery(name = "Usertable.findByName", query = "SELECT u FROM Usertable u WHERE u.username = :username")
    , @NamedQuery(name = "Usertable.findByPassword", query = "SELECT u FROM Usertable u WHERE u.password = :password")
    , @NamedQuery(name = "Usertable.findByAddress", query = "SELECT u FROM Usertable u WHERE u.address = :address")
    , @NamedQuery(name = "Usertable.findByUserId", query = "SELECT u FROM Usertable u WHERE u.uid = :uid")
    ,@NamedQuery(name = "Usertable.isUserExists", query = "SELECT COUNT(u) FROM Usertable u WHERE u.username = :username")
    ,@NamedQuery(name = "Usertable.findAllCustomer", query = "SELECT u FROM Usertable u WHERE u.usertype = :usertype")
    ,@NamedQuery(name = "Usertable.findByCustomerId", query = "SELECT u FROM Usertable u WHERE u.uid = :uid AND u.usertype = :usertype")
    ,@NamedQuery(name = "Usertable.findByCustomerName", query = "SELECT u FROM Usertable u WHERE u.username = :username AND u.usertype = :usertype")
    ,@NamedQuery(name = "Usertable.findByMessage", query = "SELECT u FROM Usertable u WHERE u.message = :message")
    , @NamedQuery(name = "Usertable.findByUsertype", query = "SELECT u FROM Usertable u WHERE u.usertype = :usertype")})
public class Usertable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "UID")
    private Long uid;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 255)
    @Column(name = "MESSAGE")
    private String message;
    @Size(max = 255)
    @Column(name = "USERTYPE")
    private String usertype;

    public Usertable() {
    }

    public Usertable(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usertable)) {
            return false;
        }
        Usertable other = (Usertable) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Usertable[ uid=" + uid + " ]";
    }
    
}
