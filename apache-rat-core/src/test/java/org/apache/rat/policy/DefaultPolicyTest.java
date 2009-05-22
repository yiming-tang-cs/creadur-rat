/*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package org.apache.rat.policy;

import junit.framework.TestCase;

import org.apache.rat.document.IDocument;
import org.apache.rat.document.MockLocation;
import org.apache.rat.report.claim.IClaim;
import org.apache.rat.report.claim.LicenseFamilyName;
import org.apache.rat.report.claim.impl.LicenseApprovalClaim;
import org.apache.rat.report.claim.impl.LicenseFamilyClaim;
import org.apache.rat.report.claim.impl.xml.CustomClaim;
import org.apache.rat.report.claim.impl.xml.MockClaimReporter;


public class DefaultPolicyTest extends TestCase {

    MockClaimReporter reporter;
    DefaultPolicy policy;
    
    protected void setUp() throws Exception {
        super.setUp();
        reporter = new MockClaimReporter();
        policy = new DefaultPolicy(reporter);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testOtherPredicate() throws Exception {
        final IDocument subject = new MockLocation("subject");
        final String predicate = "predicate";
        final String object = "object";
        policy.claim(new CustomClaim(subject, predicate, object, true));
        assertEquals("No claim", 0, reporter.claims.size());
    }

    public void testASLFamily() throws Exception {
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.APACHE_SOFTWARE_LICENSE_NAME));
        assertEquals("Approved claim", 1, reporter.claims.size());
        assertApproval(true);
    }

    private void assertApproval(boolean pApproved) {
        final IClaim claim = (IClaim) reporter.claims.get(0);
        assertApproval(pApproved, claim);
    }

    private void assertApproval(boolean pApproved, final IClaim claim) {
        assertTrue("Expected LicenseApprovalClaim, got " + claim.getClass().getName(), claim instanceof LicenseApprovalClaim);
        if (pApproved) {
            assertTrue("Expected Approval", ((LicenseApprovalClaim) claim).isApproved());
        } else {
            assertFalse("Expected Decline", ((LicenseApprovalClaim) claim).isApproved());
        }
    }
    
    public void testOASISFamily() throws Exception {
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.OASIS_OPEN_LICENSE_NAME));
        assertEquals("Approved claim", 1, reporter.claims.size());
        assertApproval(true);
    }
    
    public void testW3CFamily() throws Exception {
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.W3C_SOFTWARE_COPYRIGHT_NAME));
        assertEquals("Approved claim", 1, reporter.claims.size());
        assertApproval(true);
    }
    
    public void testW3CDocFamily() throws Exception {
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.W3C_DOCUMENT_COPYRIGHT_NAME));
        assertEquals("Approved claim", 1, reporter.claims.size());
        assertApproval(true);
    }
    
    public void testUnknownFamily() throws Exception {
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.UNKNOWN_LICENSE_FAMILY));
        assertEquals("Approved claim", 1, reporter.claims.size());
        assertApproval(false);
    }
    
    public void testCustomNames() throws Exception {
        reporter = new MockClaimReporter();
        LicenseFamilyName[] custom = {new LicenseFamilyName("Example")};
        policy = new DefaultPolicy(reporter, custom);
        final IDocument subject = new MockLocation("subject");
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.UNKNOWN_LICENSE_FAMILY));
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.W3C_DOCUMENT_COPYRIGHT_NAME));
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.W3C_SOFTWARE_COPYRIGHT_NAME));
        policy.claim(new LicenseFamilyClaim(subject, LicenseFamilyName.APACHE_SOFTWARE_LICENSE_NAME));
        assertEquals("Four unapproved claims", 4, reporter.claims.size());
        assertApproval(false, (IClaim) reporter.claims.get(0));
        assertApproval(false, (IClaim) reporter.claims.get(1));
        assertApproval(false, (IClaim) reporter.claims.get(2));
        assertApproval(false, (IClaim) reporter.claims.get(3));
        policy.claim(new LicenseFamilyClaim(subject, custom[0]));
        assertEquals("Approved claim", 5, reporter.claims.size());
        assertApproval(true, (IClaim) reporter.claims.get(4));
    }
}
