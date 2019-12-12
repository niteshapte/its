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

import com.discovery.interstellar.transport.system.entity.Traffic;

import java.util.List;

/**
 * The Class TrafficDao.
 */
@SuppressWarnings("deprecation")
@Repository
@Transactional
public class TrafficDao {

    /** The session factory. */
    private SessionFactory sessionFactory;

    /**
     * Instantiates a new traffic dao.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public TrafficDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Save.
     *
     * @param traffic the traffic
     */
    public void save(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        session.save(traffic);
    }

    /**
     * Update.
     *
     * @param traffic the traffic
     */
    public void update(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(traffic);
    }

    /**
     * Delete.
     *
     * @param routeId the route id
     * @return the int
     */
    public int delete(String routeId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM traffic AS T WHERE T.routeId = :routeIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("routeIdParameter", routeId);

        return query.executeUpdate();
    }

    /**
     * Select unique.
     *
     * @param routeId the route id
     * @return the traffic
     */
    public Traffic selectUnique(String routeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        criteria.add(Restrictions.eq("routeId", routeId));

        return (Traffic) criteria.uniqueResult();
    }

    /**
     * Select all.
     *
     * @return the list
     */
    public List<Traffic> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        List<Traffic> edges = (List<Traffic>) criteria.list();

        return edges;
    }

    /**
     * Select max record id.
     *
     * @return the long
     */
    public long selectMaxRecordId() {
        long maxId = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Traffic.class)
                .setProjection(Projections.rowCount()).uniqueResult();

        return maxId;
    }

    /**
     * Traffic exists.
     *
     * @param traffic the traffic
     * @return the list
     */
    public List<Traffic> trafficExists(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        criteria.add(Restrictions.ne("routeId", traffic.getRouteId()));
        criteria.add(Restrictions.eq("source", traffic.getSource()));
        criteria.add(Restrictions.eq("destination", traffic.getDestination()));
        List<Traffic> traffics = (List<Traffic>) criteria.list();

        return traffics;
    }
}
