package com.jn.compute.models

/** c++ model input is like
 * struct CategoryConfig {
    std::string tag;
    double weight = 1.0;
    double softness = 50.0; 
    double minAmount = 0.0;
    double maxAmount = 1e9;
};
**/
data class TagConfig(
    val tag: String,
    val weight: Double,
    val softness: Double,
    val minAmount: Double,
    val maxAmount: Double
)