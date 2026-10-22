package org.study.QualaPodo.qpx;

import org.study.QualaPodo.qpx.qpEvent.qpxComponentEvent;

public interface qpxComponentAction {
	void onResize(qpxComponentEvent e);
	void onRelocation(qpxComponentEvent e);
	void onShown(qpxComponentEvent e);
	void onHidden(qpxComponentEvent e);
	void onClosing(qpxComponentEvent e);
	void onClosed(qpxComponentEvent e);
}
