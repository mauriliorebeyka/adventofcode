package adventofcode.solutions.year2022;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.ChallengeDetails;

@ChallengeDetails(year = 2022, day = 7)
public class Day7 extends AbstractChallenge<String> {

	private PseudoFile root = new PseudoFile(null, "dir", "/");

	private PseudoFile current = root;

	@Override
	public String processA() {
		return Integer
				.toString(findFoldersBySize(root, i -> i < 100000).stream().mapToInt(f -> f.getDiskSpace()).sum());
	}

	@Override
	public String processB() {
		int spaceToFree = 30000000 - (70000000 - root.getDiskSpace());
		return Integer.toString(findFoldersBySize(root, i -> i > spaceToFree).stream().mapToInt(i -> i.getDiskSpace())
				.sorted().findFirst().getAsInt());
	}

	public List<PseudoFile> findFoldersBySize(PseudoFile folder, Predicate<Integer> maxSize) {
		List<PseudoFile> files = new ArrayList<>();
		int size = folder.getDiskSpace();
		if (maxSize.test(size)) {
			files.add(folder);
		}
		for (PseudoFile file : folder.ls()) {
			files.addAll(findFoldersBySize(file, maxSize));
		}
		return files;
	}

	@Override
	public String parse(String entry) {
		String[] data = entry.split(" ");
		if (data[0].equals("$")) {
			if (data[1].equals("cd")) {
				switch (data[2]) {
				case "/":
					current = root;
					break;
				case "..":
					current = current.parent;
					break;
				default:
					current = current.get(data[2]);
				}
			}
		} else {
			current.contents.add(new PseudoFile(current, data));
		}
		return entry;
	}

	final class PseudoFile {
		PseudoFile parent;
		List<PseudoFile> contents;
		String name;
		int size;

		public PseudoFile(PseudoFile parent, String... contents) {
			this.parent = parent;
			this.size = contents[0].equals("dir") ? -1 : Integer.parseInt(contents[0]);
			this.name = contents[1];
			this.contents = new ArrayList<>();
		}

		public int getDiskSpace() {
			int size = this.contents.stream().filter(f -> f.size > -1).mapToInt(f -> f.size).sum();
			size += this.ls().stream().mapToInt(f -> f.getDiskSpace()).sum();
			return size;
		}

		public PseudoFile get(String name) {
			return contents.stream().filter(f -> f.name.equals(name)).findFirst().get();
		}

		public List<PseudoFile> ls() {
			return this.contents.stream().filter(f -> f.size == -1).collect(Collectors.toList());
		}
	}

	@Test
	public void test() {
		String raw = """
				$ cd /
				$ ls
				dir a
				14848514 b.txt
				8504156 c.dat
				dir d
				$ cd a
				$ ls
				dir e
				29116 f
				2557 g
				62596 h.lst
				$ cd e
				$ ls
				584 i
				$ cd ..
				$ cd ..
				$ cd d
				$ ls
				4060174 j
				8033020 d.log
				5626152 d.ext
				7214296 k
								""";
		Stream.of(raw.split("\n")).forEach(this::parse);
		System.out.println(root.getDiskSpace());
		System.out.println(processA());
		System.out.println(processB());
	}
}
