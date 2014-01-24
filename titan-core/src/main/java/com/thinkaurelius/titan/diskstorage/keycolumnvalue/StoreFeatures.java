package com.thinkaurelius.titan.diskstorage.keycolumnvalue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.thinkaurelius.titan.diskstorage.configuration.Configuration;

/**
 * Specifies the features that a given store supports
 * <p/>
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 */

public class StoreFeatures {

    public Boolean supportsUnorderedScan;
    public Boolean supportsOrderedScan;
    public Boolean supportsBatchMutation;
    public Boolean supportsMultiQuery;

    public Boolean supportsTxIsolation;
    public Boolean supportsStrongConsistency;
    public Boolean supportsLocking;

    public Boolean isKeyOrdered;
    public Boolean isDistributed;
    public Boolean hasLocalKeyPartition;

    public Configuration strongConsistencyConfig;
    public Configuration localStrongConsistencyConfig;

    private boolean verify() {
        for (Field f : getClass().getDeclaredFields()) {
            try {
                if (f.get(this) == null) return false;
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Could not inspect setting: " + f.getName(), e);
            }
        }
        return true;
    }

    @Override
    public StoreFeatures clone() {
        StoreFeatures newfeatures = new StoreFeatures();
        for (Field f : getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                try {
                    f.set(newfeatures, f.get(this));
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Could not copy setting: " + f.getName(), e);
                }
            }
        }
        return newfeatures;
    }

    public static StoreFeatures defaultFeature(boolean value) {
        StoreFeatures newfeatures = new StoreFeatures();
        for (Field f : StoreFeatures.class.getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                try {
                    f.set(newfeatures, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Could not read setting: " + f.getName(), e);
                }
            }
        }
        return newfeatures;
    }

    /**
     * Whether this storage backends supports scanning of any kind
     *
     * @return
     */
    public boolean supportsScan() {
        return supportsOrderedScan() || supportsUnorderedScan();
    }


    /**
     * Whether this storage backend supports global key scans via {@link KeyColumnValueStore#getKeys(SliceQuery, StoreTransaction)}
     *
     * @return
     */
    public boolean supportsUnorderedScan() {
        assert verify();
        return supportsUnorderedScan;
    }

    /**
     * Whether this storage backend supports global key scans via {@link KeyColumnValueStore#getKeys(KeyRangeQuery, StoreTransaction)}
     *
     * @return
     */
    public boolean supportsOrderedScan() {
        assert verify();
        return supportsOrderedScan;
    }

    /**
     * Whether this storage backend supports query operations on multiple keys,
     * i.e {@link KeyColumnValueStore#getSlice(java.util.List, SliceQuery, StoreTransaction)}
     *
     * @return
     */
    public boolean supportsMultiQuery() {
        assert verify();
        return supportsMultiQuery;
    }

    /**
     * Whether this storage backend supports transactions that provide transactional isolation. In
     * other words, the backend transactions are isolated from each other.
     *
     * @return
     */
    public boolean supportsTxIsolation() {
        assert verify();
        return supportsTxIsolation;
    }

    public boolean supportsStrongConsistency() {
        assert verify();
        return supportsStrongConsistency;
    }

    public Configuration getStrongConsistencyTxConfig() {
        assert verify();
        return strongConsistencyConfig;
    }

    public Configuration getLocalStrongConsistencyTxConfig() {
        assert verify();
        return localStrongConsistencyConfig;
    }

    /**
     * Whether this store supports locking via {@link KeyColumnValueStore#acquireLock(com.thinkaurelius.titan.diskstorage.StaticBuffer, com.thinkaurelius.titan.diskstorage.StaticBuffer, com.thinkaurelius.titan.diskstorage.StaticBuffer, StoreTransaction)}
     *
     * @return
     */
    public boolean supportsLocking() {
        assert verify();
        return supportsLocking;
    }

    /**
     * Whether this storage backend supports batch mutations via {@link KeyColumnValueStoreManager#mutateMany(java.util.Map, StoreTransaction)}.
     *
     * @return
     */
    public boolean supportsBatchMutation() {
        assert verify();
        return supportsBatchMutation;
    }

    public boolean isKeyOrdered() {
        assert verify();
        return isKeyOrdered;
    }

    public boolean isDistributed() {
        assert verify();
        return isDistributed;
    }

    public boolean hasLocalKeyPartition() {
        assert verify();
        return hasLocalKeyPartition;
    }
}
