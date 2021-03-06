# Copyright (c) 2005, 2019 Oracle and/or its affiliates.  All rights reserved.
#
# See the file LICENSE for license information.
#
# $Id$
#
# TEST	fop007
# TEST	Test file system operations on named in-memory databases.
# TEST	Combine two ops in one transaction.
proc fop007 { method args } {

	# Queue extents are not allowed with in-memory databases.
	if { [is_queueext $method] == 1 } {
		puts "Skipping fop007 for method $method."
		return
	}
	eval {fop001 $method 1 0} $args
}



