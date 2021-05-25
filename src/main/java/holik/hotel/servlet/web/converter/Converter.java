package holik.hotel.servlet.web.converter;

public interface Converter<S,T> {
    T convertToEntity(S entity);
}
