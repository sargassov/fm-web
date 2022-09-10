package ru.sargassov.fmweb.constants;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Collection;

@Getter
@Setter
@MappedSuperclass
public class IntermediateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        var objClass = (obj instanceof HibernateProxy)
                ? obj.getClass().getSuperclass()
                : obj.getClass();
        if (getClass() != objClass) {
            return false;
        }
        var other = (IntermediateEntity) obj;
        if (this.getId() == null) {
            return other.getId() == null;
        } else {
            return this.getId().equals(other.getId());
        }
    }

    protected int hashOfCollection(Collection<?> collection) {
        var hashCode = 1;
        if (collection instanceof AbstractPersistentCollection) {
            if (((AbstractPersistentCollection) collection).wasInitialized()) {
                for (Object obj : collection) {
                    hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
                }
            } else {
                return 0;
            }
        } else {
            if (!collection.isEmpty()) {
                for (Object obj : collection) {
                    hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
                }
            } else {
                hashCode = 0;
            }
        }
        return hashCode;
    }
}
