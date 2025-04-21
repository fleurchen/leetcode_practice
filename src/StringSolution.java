public class StringSolution {

    // 反转字符串，使用双指针，两头进行互相替换，实现反转
    public void reverseString(char[] s) {
        int i=0,j=s.length-1;
        while(i<j){
            char temp=s[i];
            s[i] = s[j];
            s[j]=temp;
            i++;
            j--;
        }
    }
    // 反转字符串二 ，前2k个数，反转前k个，当不够2k的时候，如果大于k，则反转前k个，否则全部反转
    public String reverseString(String s,int k){
        char[] chars = s.toCharArray();
        int size = chars.length;
        for(int i=0;i<size;i+=2*k){
            int start=i;
            // 要么最长到表尾，要么最长到k个位置
            int end = Math.min(size-1,start+k-1);
            while(start<end){
                char temp=chars[start];
                chars[start]=chars[end];
                chars[end]=temp;
                start++;
                end--;
            }
        }
        return new String(chars);
    }

    // 替换数字，将字符中数字都替换为number，采用从后往前替换的方式
    public String replaceNum(String s){
        int count=0;
        int oldSize = s.length();
        char[] chars = s.toCharArray();
        for(int i=0;i<oldSize;i++){
            if(Character.isDigit(chars[i])){
                count++;
            }
        }

        char[] sNew = new char[oldSize+count*5];
        int newSize = sNew.length;
        System.arraycopy(chars,0,sNew,0,oldSize);
        for(int i=oldSize-1,j=newSize-1;i<j;i--,j--){
            if(!Character.isDigit(chars[i])){
                sNew[j] = chars[i];
            }else{
                sNew[j] = 'r';
                sNew[j-1] = 'e';
                sNew[j-2] = 'b';
                sNew[j-3] = 'm';
                sNew[j-4] = 'u';
                sNew[j-5] = 'n';
                j-=5;
            }
        }
        return new String(sNew);
    }

    // 将单个单词置换顺序，采取的算法为将整体全部倒序，然后每个单词倒序
    public String reverseString2(String s){
        // 将多余的空格排除
        String noSpaceStr = removeSpace(s);
        String reversedStr = reverseString(noSpaceStr,0,noSpaceStr.length());
        String result = reverseWord(reversedStr);
        return result;
    }

    public String removeSpace(String s){
        StringBuilder sb = new StringBuilder();
        int start = 0;
        int end = s.length()-1;
        // 先去掉头尾的空格
        while(s.charAt(start)==' ') start++;
        while(s.charAt(end)==' ') end--;
        for(;start<=end;start++){
            char c = s.charAt(start);
            if(c!=' ' || sb.charAt(sb.length()-1)!=' '){
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String reverseString(String s,int start,int end){
        char[] chars = s.toCharArray();
        while(start<end){
            char temp=chars[start];
            chars[start]=chars[end];
            chars[end]=temp;
        }
        return new String(chars);
    }

    public String reverseWord(String s){
        int start =0;
        StringBuilder result = new StringBuilder();
        for(;start<s.length();start++){
            if (s.charAt(start)==' '){
                int end = start-1;
                result.append(reverseString(s,start,end)).append(" ");
                start = end;
            }
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    // 右旋字符串 将整个字符串反转，然后分别反转分割的两块字符串

    public String rightSwap(String s,int n){
        int len = s.length();
        String tran1 = reverseString(s,0,len-1);
        tran1 = reverseString(tran1,0,n-1);
        tran1 = reverseString(tran1,n,len-1);
        return tran1;
    }

    //实现字符串种包含的字串的第一次出现的位置，使用kmp算法
    public int strStr(String haystack,String needle){
        int[] next = new int[needle.length()];
        getNext(next,needle);
        char[] hChar = haystack.toCharArray();
        char[] nChar = needle.toCharArray();
        int j = 0;
        for(int i=0;i<hChar.length;i++){
            while(j>0&&hChar[i]!=nChar[j]){ j=next[j-1];}
            if(hChar[i]==nChar[j]){
                j++;
            }
            if(j==nChar.length){
                return i-nChar.length+1;
            }
        }
        return -1;

    }

    public void getNext(int[] next,String needle){
        char[] chars = needle.toCharArray();
        int j=0;
        next[0] = 0;
        for(int i=1;i<chars.length;i++){
            while(j>0 && chars[j]!=chars[i]){j=next[j-1];}
            if(chars[j]==chars[i]){
                j++;
            }
            next[i] = j;
        }

    }

    // 判断字符串是否由字符串的子串重复组成
    // 利用kmp算法，当字符串的长度可以整除（字符串的长度-最长相等前缀） 时，表示存在重复子串
    public boolean repeatedSubstring(String s){
        int n = s.length();

        int[] next = new int[n];
        int j = 0;
        // kmp算法
        next[0] = 0;
        for(int i=1;i<n;i++){
            if(j>0 && s.charAt(j)!=s.charAt(i)){
                j = next[j-1];
            }
            if(s.charAt(i)==s.charAt(j)){
                j++;
            }
            next[i]=j;
        }

        if(next[n-1]>0 && n%(n-next[n-1])==0){
            return true;
        }else return false;
    }
}
