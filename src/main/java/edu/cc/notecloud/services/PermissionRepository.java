package edu.cc.notecloud.services;

import edu.cc.notecloud.security.Permission;
import edu.cc.notecloud.exception.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class PermissionRepository {

    @Inject
    private CrudGenericService crudService;

    public List<Permission> findAll(){
        return crudService.findWithNativeQuery("select * from permission", Permission.class);
    }

    public Permission find(Long id) throws EntityNotFoundException {
        Permission entity = crudService.find(Permission.class, id);
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException("Permission not found [" + id + "]");
    }
}
