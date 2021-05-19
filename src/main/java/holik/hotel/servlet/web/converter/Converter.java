package holik.hotel.servlet.web.converter;

public interface Converter<S,T> {
    T covertToEntity(S entity);
}
