package ru.hzerr.fx.engine.core.path;

public final class LocationTools {

    public static String resolve(ResolvableLocation firstLocation, ResolvableLocation secondLocation, SeparatorResolveLocationOptions resolveLocationOptions, Separator separator) {
        if (firstLocation.getLocation() != null) {
            if (secondLocation.getLocation() != null) {
                String targetLocation = prependInEndSeparatorIfNeeded(firstLocation.getLocation(), separator) + secondLocation.getLocation();

                return switch (resolveLocationOptions) {
                    case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(targetLocation, separator);
                    case INSERT_START -> prependInStartSeparatorIfNeeded(targetLocation, separator);
                    case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(targetLocation, separator);
                    case START_REMOVE -> removeInStartSeparatorIfNeeded(targetLocation, separator);
                    case INSERT_END -> prependInEndSeparatorIfNeeded(targetLocation, separator);
                    case END_REMOVE -> removeInEndSeparatorIfNeeded(targetLocation, separator);
                    case DEFAULT -> targetLocation;
                };
            } else return switch (firstLocation.getNullSafeOptions()) {
                case INSERT_START -> prependInStartSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case INSERT_END -> prependInEndSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case REMOVE_START -> removeInStartSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case REMOVE_END -> removeInEndSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(firstLocation.getLocation(), separator);
                case THROW_ILLEGAL_ARGUMENT_EXCEPTION -> throw new IllegalArgumentException("The second argument can't be null");
                case DEFAULT -> firstLocation.getLocation();
            };
        } else if (secondLocation != null) {
            return switch (secondLocation.getNullSafeOptions()) {
                case INSERT_START -> prependInStartSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case INSERT_END -> prependInEndSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case INSERT_EVERYWHERE -> prependEverywhereSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case REMOVE_START -> removeInStartSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case REMOVE_END -> removeInEndSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case REMOVE_EVERYWHERE -> removeEverywhereSeparatorIfNeeded(secondLocation.getLocation(), separator);
                case THROW_ILLEGAL_ARGUMENT_EXCEPTION -> throw new IllegalArgumentException("The first argument can't be null");
                case DEFAULT -> secondLocation.getLocation();
            };
        } else
            throw new IllegalArgumentException("The first and second arguments cannot be null");
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
