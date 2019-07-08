package com.project.OOP.errors;

/**
 * Classe che va ad implementare un errore personalizzato, che viene lanciato nel caso in cui uno o più comandi nel filtro
 * non corrispondono con quelli richiesti
 */
public class CommandNotFoundException extends Exception {

    /**
     * Costruttore della classe
     * @param message Messaggio dell'errore, utilizzato per informare l'utente dell'errore
     */
    public CommandNotFoundException(String message) {
        super(message);
    }
}
