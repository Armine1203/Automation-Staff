package de_serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDatas {
    private String color;
    private String capacity;
    private int capacityGB;
    private float price;
    private String generation;
    private int year;
    private String cpuModel;
    private String hardDiskSize;
    private String strapColour;
    private String caseSize;
    private String description;
    private float screenSize;

    @JsonProperty("color")
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    @JsonProperty("Color")
    public void setColorAlt(String color) { this.color = color; }

    @JsonProperty("capacity")
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }

    @JsonProperty("Capacity")
    public void setCapacityAlt(String capacity) { this.capacity = capacity; }

    @JsonProperty("capacity GB")
    public int getCapacityGB() { return capacityGB; }
    public void setCapacityGB(int capacityGB) { this.capacityGB = capacityGB; }

    @JsonProperty("price")
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public void setPrice(String price) {
         this.price = Float.parseFloat(price);
    }

    @JsonProperty("generation")
    public String getGeneration() { return generation; }
    public void setGeneration(String generation) { this.generation = generation; }

    @JsonProperty("Generation")
    public void setGenerationAlt(String generation) { this.generation = generation; }

    @JsonProperty("year")
    public int getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    @JsonProperty("CPU model")
    public String getCpuModel() { return cpuModel; }
    public void setCpuModel(String cpuModel) { this.cpuModel = cpuModel; }

    @JsonProperty("Hard disk size")
    public String getHardDiskSize() { return hardDiskSize; }
    public void setHardDiskSize(String hardDiskSize) { this.hardDiskSize = hardDiskSize; }

    @JsonProperty("Strap Colour")
    public String getStrapColour() { return strapColour; }
    public void setStrapColour(String strapColour) { this.strapColour = strapColour; }

    @JsonProperty("Case Size")
    public String getCaseSize() { return caseSize; }
    public void setCaseSize(String caseSize) { this.caseSize = caseSize; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("Screen size")
    public float getScreenSize() { return screenSize; }
    public void setScreenSize(float screenSize) { this.screenSize = screenSize; }


}
