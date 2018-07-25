package com.hsms.domain.common;

import java.util.List;

/**
 * The {@link Floor} class represent a hotel floor.
 * <p>
 * A {@link Floor} entity consists of List of {@link MainCorridor} and {@link SubCorridor}
 * <p>
 * This class provides methods to operate on {@link MainCorridor} list and {@link SubCorridor} list of a {@link Floor}
 */
public class Floor {
    private final int floorId;
    private final List<MainCorridor> mainCorridors;
    private final List<SubCorridor> subCorridors;

    private Floor(final Builder builder) {
        this.floorId = builder.floorId;
        this.mainCorridors = builder.mainCorridors;
        this.subCorridors = builder.subCorridors;
    }

    public List<MainCorridor> getMainCorridors() {
        return mainCorridors;
    }

    public List<SubCorridor> getSubCorridors() {
        return subCorridors;
    }

    public int getFloorId() {
        return this.floorId;
    }

    public static class Builder {

        private int floorId;
        private List<MainCorridor> mainCorridors;
        private List<SubCorridor> subCorridors;

        public Builder setFloorId(final int floorId) {
            this.floorId = floorId;
            return this;
        }

        public Builder setMainCorridors(final List<MainCorridor> mainCorridors) {
            this.mainCorridors = mainCorridors;
            return this;
        }

        public Builder setSubCorridors(final List<SubCorridor> subCorridors) {
            this.subCorridors = subCorridors;
            return this;
        }

        public Floor build() {
            return new Floor(this);
        }
    }
}
