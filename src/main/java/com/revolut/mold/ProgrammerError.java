package com.revolut.mold;

import static java.lang.String.format;

/**
 * A <code>ProgrammerError</code> should be thrown in cases when an exceptional situation that took place was made
 * possible only and exclusively as a result of a bug in some part of the system. Mostly such cases are manifested by
 * inconsistent state/data as a result of a bug or insufficient validation.
 * <p/>
 * It may seem that <code>java.lang.IllegalStateException</code> is a good candidate for such scenarios.
 * However, the "illegal state" situation indicates temporary malfunction, i.e. the system can restore normal operation
 * (by retrying or restarting), whereas a "programmer error" indicates a permanent bug that requires changing the code
 * of the system and potentially fixing persisted state inconsistencies.
 * <p/>
 * Client side errors like an incorrect request that does not pass validation or cause a race condition, even if caused
 * by a bug introduced by a client code programmer, are not considered for <code>ProgrammerError</code> as the state
 * correctness is preserved and the system code does not need to be changed.
 * <p/>
 * A good example of a <code>ProgrammerError</code> scenario is a case when a query by a unique value returns multiple
 * results. This would indicate that appropriate constraints validation is not implemented in a different part of
 * the system (for instance, a missing primary key or unique index on the database level).
 * <p/>
 * In summary, programmers should at all times put all efforts to prevent <code>ProgrammerError</code>s to ever be
 * raised during run time; but we should also be mindful that human errors do happen, and we should be able to find this
 * out soonest.
 */
public class ProgrammerError extends RuntimeException {
    public ProgrammerError(String message, Object... params) {
        super(format(message, params));
    }
}