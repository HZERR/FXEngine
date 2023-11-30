package ru.hzerr.fx.engine.core.path;

public final class LocationTools {

    public static String resolve(ResolvableLocation location1, ResolvableLocation location2, SeparatorResolveLocationOptions resolveLocationOptions, Separator separator) {
        if (location1.getLocation() != null) {
            if (location2.getLocation() != null) {
                String targetLocation = prependInEndSeparatorIfNeeded(location1.getLocation(), separator) + location2.getLocation();

                return switch (resolveLocationOptions) {
                    case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(targetLocation, separator);
                    case INSERT_START -> prependInStartSeparatorIfNeeded(targetLocation, separator);
                    case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(targetLocation, separator);
                    case START_REMOVE -> removeInStartSeparatorIfNeeded(targetLocation, separator);
                    case INSERT_END -> prependInEndSeparatorIfNeeded(targetLocation, separator);
                    case END_REMOVE -> removeInEndSeparatorIfNeeded(targetLocation, separator);
                    case DEFAULT -> targetLocation;
                };
            } else return switch (location1.getNullSafeOptions()) {
                case INSERT_START -> prependInStartSeparatorIfNeeded(location1.getLocation(), separator);
                case INSERT_END -> prependInEndSeparatorIfNeeded(location1.getLocation(), separator);
                case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(location1.getLocation(), separator);
                case REMOVE_START -> removeInStartSeparatorIfNeeded(location1.getLocation(), separator);
                case REMOVE_END -> removeInEndSeparatorIfNeeded(location1.getLocation(), separator);
                case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(location1.getLocation(), separator);
                case THROW_EXCEPTION -> throw new IllegalArgumentException("Location2 can't be null");
                case DEFAULT -> location1.getLocation();
            };
        } else if (location2 != null) {
            return switch (location2.getNullSafeOptions()) {
                case INSERT_START -> prependInStartSeparatorIfNeeded(location2.getLocation(), separator);
                case INSERT_END -> prependInEndSeparatorIfNeeded(location2.getLocation(), separator);
                case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(location2.getLocation(), separator);
                case REMOVE_START -> removeInStartSeparatorIfNeeded(location2.getLocation(), separator);
                case REMOVE_END -> removeInEndSeparatorIfNeeded(location2.getLocation(), separator);
                case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(location2.getLocation(), separator);
                case THROW_EXCEPTION -> throw new IllegalArgumentException("Location1 can't be null");
                case DEFAULT -> location2.getLocation();
            };
        } else
            throw new IllegalArgumentException("Location1 && Location2 can't be null");
    }

    private static String prependInStartSeparatorIfNeeded(String location, Separator separator) {
        if (!location.startsWith(separator.getSeparator())) {
            return separator.getSeparator() + location;
        }

        return location;
    }

    private static String prependInEndSeparatorIfNeeded(String location, Separator separator) {
        if (!location.endsWith(separator.getSeparator())) {
            return location + separator.getSeparator();
        }

        return location;
    }

    private static String prependEverywhereSeparatorIfNeeded(String location, Separator separator) {
        if (!location.startsWith(separator.getSeparator())) {
            location = separator.getSeparator() + location;
        }

        if (!location.endsWith(separator.getSeparator())) {
            location = location + separator.getSeparator();
        }

        return location;
    }

    private static String removeInStartSeparatorIfNeeded(String location, Separator separator) {
        if (location.startsWith(separator.getSeparator())) {
            return location.substring(separator.getSeparator().length());
        }

        return location;
    }

    private static String removeInEndSeparatorIfNeeded(String location, Separator separator) {
        if (location.endsWith(separator.getSeparator())) {
            return location.substring(0, location.length() - separator.getSeparator().length());
        }

        return location;
    }

    private static String removeEverywhereSeparatorIfNeeded(String location, Separator separator) {
        if (location.startsWith(separator.getSeparator())) {
            location = location.substring(separator.getSeparator().length());
        }

        if (location.endsWith(separator.getSeparator())) {
            location = location.substring(0, location.length() - separator.getSeparator().length());
        }

        return location;
    }
}
