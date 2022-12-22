package de.hsbremen.mkss.restservice.exceptions;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {
        super("Could not find item " + id);
    }
}
