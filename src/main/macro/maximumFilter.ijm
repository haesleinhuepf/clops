// Run maximum filter via Mops and ImageJ-ops 
//
// Author: Robert Haase, rhaase@mpi-cbg.de
// June 2019
//

run("Close All");

// get test image
run("Blobs (25K)");
run("32-bit");
rename("blobs");

run("Duplicate...", "title=blobs2");

// init GPU
run("CLIJ Macro Extensions", "cl_device=");

Ext.MOPS_maximumSphere("blobs", "blobs2", 4);

