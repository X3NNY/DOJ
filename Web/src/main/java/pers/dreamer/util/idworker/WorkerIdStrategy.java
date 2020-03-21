package pers.dreamer.util.idworker;

public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}
