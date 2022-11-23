package com.example.jumpstart.ecommerce.config;


import com.example.jumpstart.ecommerce.entities.audit.Revision;
import org.hibernate.envers.RevisionListener;

public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        final Revision revision=(Revision) revisionEntity;
    }
}
