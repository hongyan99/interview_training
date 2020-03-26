package string;

public class RegEx {
	public static void main(String[] args) {
//		System.out.println(pattern_matcher("abbbc", "ab*c"));
//		System.out.println(pattern_matcher("abcdefg", "a.c.*.*gg*"));
//		System.out.println(pattern_matcher("", "a*b*c*.*g*"));
//		System.out.println(pattern_matcher("", ".*."));
		System.out.println(pattern_matcher("abcdefghijkl", ".*...*z*kl*l."));
	}
	
	static boolean pattern_matcher(String text, String pattern) {
		return new RootMatcher(pattern).match(text, 0);
    }
	
	private abstract static class Matcher {
		protected final Matcher next;
		
		abstract boolean match(String text, int pos);
		protected Matcher(final String pattern) {
			if(pattern==null) {
				this.next = null;
			} else if (pattern.length()==0) {
				this.next = new TerminalMatcher();
			} else if (pattern.length()>1 && pattern.charAt(1)=='*') {
				this.next = new RepeatMatcher(pattern.substring(2), pattern.charAt(0));
			}  else if(pattern.charAt(0)=='.') {
				this.next = new DotMatcher(pattern.substring(1));
			} else if(isLetter(pattern.charAt(0))) {
				int idx = 1;
				while(idx<pattern.length()) {
					if(pattern.charAt(idx)=='.') {
						break;
					} else if(pattern.charAt(idx)=='*') {
						idx--;
						break;
					}
					idx++;
				}
				this.next = new ExactMatcher(pattern.substring(idx), pattern.substring(0, idx));
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		private boolean isLetter(char a) {
			short diff = (short) (a-'a');
			return diff >=0 && diff <=25;
		}
	}
	
	private static class RootMatcher extends Matcher {
		public RootMatcher(String pattern) {
			super(pattern);
		}

		@Override
		boolean match(String text, int pos) {
			return next.match(text, 0);
		}
	}
	
	private static class DotMatcher extends Matcher {
		public DotMatcher(String pattern) {
			super(pattern);
		}

		@Override
		boolean match(String text, int pos) {
			if(pos>=text.length()) {
				return false;
			}
			return next.match(text, pos+1);
		}
		
	}
	
	private static class RepeatMatcher extends Matcher {
		private final char preceedChar;
		public RepeatMatcher(String pattern, char preceedChar) {
			super(pattern);
			this.preceedChar = preceedChar;
		}

		@Override
		boolean match(String text, int pos) {
			if(pos>text.length()) {
				return false;
			}
			if(pos==text.length() && preceedChar!='.') {
				return true;
			}
			
			while(preceedChar=='.' || text.charAt(pos)==preceedChar) {
				if(next.match(text, pos)) {
					return true;
				}
				pos++;
				if(pos>=text.length()) {
					break;
				}
			}
			return next.match(text, pos);
		}
		
	}
	
	private static class ExactMatcher extends Matcher {
		private final String match;
		public ExactMatcher(String pattern, String match) {
			super(pattern);
			this.match=match;
		}

		@Override
		boolean match(String text, int pos) {
			if(!text.substring(pos).startsWith(match)) {
				return false;
			}
			return next.match(text, pos+match.length());
		}
		
	}
	
	private static class TerminalMatcher extends Matcher {
		public TerminalMatcher() {
			super(null);
		}

		@Override
		boolean match(String text, int pos) {
			return pos==text.length();
		}
	}
}
