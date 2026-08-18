
import java.util.Objects;

class LibraryMember {

    private String memberId;
    private String name;
    private String email;

    public LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryMember other = (LibraryMember) obj;
        return Objects.equals(memberId, other.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    @Override
    public String toString() {
        return "LibraryMember{"
                + "memberId='" + memberId + '\''
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + '}';
    }
}

public class MemberEqualityPractice {

    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember("M001", "張三", "zhang@example.com");
        LibraryMember m2 = new LibraryMember("M001", "張三", "updated_zhang@example.com");

        System.out.println("成員 1: " + m1);
        System.out.println("成員 2: " + m2);

        System.out.println("\n=== 比較結果 ===");
        System.out.println("使用 == 比較 (記憶體位址): " + (m1 == m2));
        System.out.println("使用 equals() 比較 (memberId): " + m1.equals(m2));

        System.out.println("\n=== 邊界測試 ===");
        System.out.println("與 null 比較: " + m1.equals(null));
    }
}
