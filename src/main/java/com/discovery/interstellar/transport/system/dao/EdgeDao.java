package com.discovery.interstellar.transport.system.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.discovery.interstellar.transport.system.entity.Edge;

import java.util.List;

/**
 * The Class EdgeDao.
 */
@SuppressWarnings("deprecation")
@Repository
@Transactional
public class EdgeDao {

    /** The session factory. */
    private SessionFactory sessionFactory;

    /**
     * Instantiates a new edge dao.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public EdgeDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Save.
     *
     * @param edge the edge
     */
    public void save(Edge edge) {
        Session session = sessionFactory.getCurrentSession();
        session.save(edge);
    }

    /**
     * Update.
     *
     * @param edge the edge
     */
    public void update(Edge edge) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(edge);
    }

    /**
     * Delete.
     *
     * @param recordId the record id
     * @return the int
     */
    public int delete(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM edge AS E WHERE E.recordId = :recordIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("recordIdParameter", recordId);

        return query.executeUpdate();
    }

    /**
     * Select unique.
     *
     * @param recordId the record id
     * @return the edge
     */
    public Edge selectUnique(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        criteria.add(Restrictions.eq("recordId", recordId));

        return (Edge) criteria.uniqueResult();
    }

    /**
     * Select max record id.
     *
     * @return the long
     */
    public long selectMaxRecordId() {
        long maxId = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Edge.class)
                .setProjection(Projections.max("recordId")).uniqueResult();

        return maxId;
    }

    /**
     * Edge exists.
     *
     * @param edge the edge
     * @return the list
     */
    public List<Edge> edgeExists(Edge edge) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        criteria.add(Restrictions.ne("recordId", edge.getRecordId()));
        criteria.add(Restrictions.eq("source", edge.getSource()));
        criteria.add(Restrictions.eq("destination", edge.getDestination()));
        List<Edge> edges = (List<Edge>) criteria.list();

        return edges;
    }

    /**
     * Select all by record id.
     *
     * @param recordId the record id
     * @return the list
     */
    public List<Edge> selectAllByRecordId(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        criteria.add(Restrictions.eq("recordId", recordId));
        List<Edge> edges = (List<Edge>) criteria.list();

        return edges;
    }

    /**
     * Select all by edge id.
     *
     * @param edgeId the edge id
     * @return the list
     */
    public List<Edge> selectAllByEdgeId(String edgeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        criteria.add(Restrictions.eq("edgeId", edgeId));
        List<Edge> edges = (List<Edge>) criteria.list();

        return edges;
    }

    /**
     * Select all.
     *
     * @return the list
     */
    public List<Edge> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        List<Edge> edges = (List<Edge>) criteria.list();
        return edges;
    }
}

