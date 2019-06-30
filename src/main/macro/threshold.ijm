// Run Huange threshold via Mops and ImageJ-ops 
//
// Author: Robert Haase, rhaase@mpi-cbg.de
// June 2019
//

run("Close All");

// get test image
run("Blobs (25K)");
run("32-bit");
rename("blobs");

// init GPU
run("CLIJ Macro Extensions", "cl_device=");

Ext.MOPS_huangThreshold("blobs");

