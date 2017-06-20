package com.jgaleano.firebase;

import android.graphics.drawable.Drawable;

/**
 * Created by josimargaleanoflorez on 19/06/17.
 */

public class Notificacion {
    private String id;
    private String title;
    private String description;
    private Drawable imagen;

    public Notificacion() {
        super();
    }

    public Notificacion(String id, String title, String description, Drawable imagen) {
        super();
        this.title = title;
        this.description = description;
        this.imagen = imagen;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTittle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}
}
