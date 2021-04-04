package cz.vse.restaurace.model;

import java.io.Serializable;

/**
 * Třída User slouží pro reprezentaci účtů v aplikaci.
 *
 * @author Jonáš Matějka
 * @version ZS2020
 */
public class User implements Serializable {

    static final long serialVersionUID=1L;

    private String userName;
    private String password;

    /**
     * Kontruktor třídy.
     * @param userName String parametr pro přihlašovací jméno
     * @param password String parametr pro přihlašovací heslo
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Metoda sloužící pro vrácení přihlašovacího jména účtu
     * @return Vrací jméno/přihlašovací jméno účtu
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Metoda sloužící pro vrácení přihlašovacího hesla účtu
     * @return Vrací přihlašovací hesla účtu
     */
    public String getPassword() {
        return password;
    }
}


