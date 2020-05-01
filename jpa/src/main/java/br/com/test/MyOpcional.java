package br.com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyOpcional {

	public static void main(String[] args) {
		
		String [] words= new String[10];
		String word= words[5];
		
		if(word != null) {
			System.out.println(word.length());
		}
		
		Optional<String> checkNull= Optional.ofNullable(words[5]);
		if (checkNull.isPresent()) {
			System.out.println(word.length());
		}
		
		List<String> list= new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
		List<String> collect = list.stream().filter(s -> Integer.valueOf(s) % 2 == 0).collect(Collectors.toList());
		collect.forEach(System.out::println);
		

	}

}
