package software.aoc.day08.b.model;

public record Point3D(int x, int y, int z) {

    public long distanceSquared(Point3D other) {
        long dx = this.x - other.x;
        long dy = this.y - other.y;
        long dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
