package com.librarymanagement.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.librarymanagement.app.entities.LibraryManagementEntities;

@Repository
@Transactional
public class LibraryManagementDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<LibraryManagementEntities> search(String name) {
        Session session = null;
        name = name.trim();
        session = sessionFactory.getCurrentSession();
        // String sql = "from LibraryManagementEntities e where e.name like :name or
        // e.author like :name"
        // + "or e.edition like :name or e.price like :name or e.page like :name or
        // e.isbn like :name"
        // + "or e.available like :name";
        //
        // return session.createQuery(sql,
        // LibraryManagementEntities.class).setParameter("name", "%" + name + "%")
        // .setParameter("author", "%" + name + "%")
        // .setParameter("edition", "%" + name + "%")
        // .setParameter("price", "%" + name + "%")
        // .setParameter("page", "%" + name + "%")
        // .setParameter("isbn", "%" + name + "%")
        // .setParameter("available", "%" + name + "%")
        // .getResultList();

        Criteria criteria = session.createCriteria(LibraryManagementEntities.class);

        Criterion cName = Restrictions.like("name", "%" + name + "%");
        Criterion author = Restrictions.like("author", "%" + name + "%");
        Criterion isbn = Restrictions.like("isbn", "%" + name + "%");
        Criterion available = Restrictions.like("available", "%" + name + "%");

//        Criterion edition = Restrictions.gt("edition",new Integer(3));
//        
//        Criterion price  = Restrictions.gt("price",new Integer(25));
//        Criterion page = Restrictions.gt("page",new Integer(25));

        Disjunction orExp = Restrictions.or(cName, author, isbn, available);

        criteria.add(orExp);
        return criteria.list();

    }

    public List<LibraryManagementEntities> getAll() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            String sql = "from LibraryManagementEntities";

            return session.createQuery(sql, LibraryManagementEntities.class).getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
}
