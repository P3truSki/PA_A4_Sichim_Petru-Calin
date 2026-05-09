class SharedMemory {
    public synchronized void shareInfo(String info) {
        System.out.println("[Shared Memory] " + info);
    }
}