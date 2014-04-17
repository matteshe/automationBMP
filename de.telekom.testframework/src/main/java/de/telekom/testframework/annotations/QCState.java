package de.telekom.testframework.annotations;

/**
 *
 * @author Daniel
 */
public enum QCState {

    /**
     * unknown state
     */
    /**
     * unknown state
     */
    Unknown,
    /**
     * someone is working on it
     */
    Ongoing,
    /**
     * means ready but needs a review, because maybe the tester needs to add
     * more verifications
     */
    ReadyButNeedsReview,
    /**
     * means ready
     */
    Ready,
}
