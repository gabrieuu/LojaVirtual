package br.edu.ifrn.trab.dto;

public class AutocompleteDTO {
	
	private String label;
	
	private Integer value;

	public AutocompleteDTO(String label, Integer value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
