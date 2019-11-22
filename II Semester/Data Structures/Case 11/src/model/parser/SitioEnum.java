package model.parser;

enum SitioEnum {

    Padre(SitioPadre.class),
    Derivado(SitioDerivado.class);

    private final Class<? extends Sitio> clazz;

    SitioEnum(Class<? extends Sitio> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getType() {
        return (Class<T>) clazz;
    }

}
